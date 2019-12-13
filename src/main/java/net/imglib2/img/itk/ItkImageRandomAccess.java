package net.imglib2.img.itk;

import org.itk.simple.Image;
import org.itk.simple.VectorUInt32;

import net.imglib2.AbstractLocalizableInt;
import net.imglib2.ItkPoint;
import net.imglib2.Localizable;
import net.imglib2.Positionable;
import net.imglib2.RandomAccess;
import net.imglib2.Sampler;
import net.imglib2.type.NativeType;
import net.imglib2.type.Type;
import net.imglib2.type.itk.WrappedItkUnsignedByteType_v1;
import net.imglib2.type.numeric.real.*;
import net.imglib2.type.numeric.integer.*;

/**
 * This {@link Img} stores wraps an ITK {@link Image}.
 * 
 * @author John Bogovic
 *
 * @param <T>
 */
public class ItkImageRandomAccess<T extends NativeType<T> > extends AbstractLocalizableInt implements RandomAccess<T>
{
	protected final ItkImageImg<T,?> img;

	protected final T type;

	public ItkImageRandomAccess( ItkImageImg<T,?> itkImage ) 
	{
		super( itkImage.numDimensions() );
		this.img = itkImage;

		int index = 0;
		type = img.createLinkedType();

		//System.out.println("type: " + type);

		type.updateContainer( this );
		type.updateIndex( index );
	}

	public ItkImageImg<T,?> getContainer()
	{
		return img;
	}

	@Override
	public T get()
	{
		return type;
	}

//	public Object getOld()
//	{
//		switch( t.getClass().getName() )
//		{
//		case "DoubleType":
//			((DoubleType)t).setReal( itkImage.getPixelAsDouble( getPosition() ));
//			break;
//		case "FloatType":
//			((FloatType)t).setReal( itkImage.getPixelAsFloat( getPosition() ));
//			break;
//		case "ByteType":
//			((ByteType)t).set( itkImage.getPixelAsInt8( getPosition() ));
//			break;
//		case "UnsignedByteType":
//			((UnsignedByteType)t).set( itkImage.getPixelAsUInt8( getPosition() ));
//			break;
//		case "ShortType":
//			((ShortType)t).set( itkImage.getPixelAsInt16( getPosition() ));
//			break;
//		case "IntType":
//			((IntType)t).set( itkImage.getPixelAsInt32( getPosition() ));
//			break;
//		case "LongType":
//			((LongType)t).set( itkImage.getPixelAsInt64( getPosition() ));
//			break;
//		}
//		return t;
//	}

//	@SuppressWarnings("unchecked")
//	private static <T extends NativeType<T>, S extends T> S wrapType( final ItkImageRandomAccess<T> itkra )
//	{
//		String className = itkra.t.getClass().getName();
//		System.out.println( "getSuitableType " + className );
//		switch( className )
//		{
//		case "net.imglib2.type.numeric.integer.UnsignedByteType":
//			return (S) new WrappedItkUnsignedByteType_v1( (ItkImageRandomAccess<UnsignedByteType>)itkra );
//		default:
//			System.err.println("Type " + className + " not yet supported.");
//		}
//		return null;
//	}

	@Override
	public Sampler<T> copy() {
		return new ItkImageRandomAccess<T>( img );
	}

	@Override
	public RandomAccess<T> copyRandomAccess() {
		return new ItkImageRandomAccess<T>( img );
	}

	@Override
	public void move(int[] distance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(long[] distance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPosition(Localizable localizable) {
		int index = 0;
		for ( int d = 0; d < n; ++d )
		{
			position[ d ] = localizable.getIntPosition( d );
			index += position[ d ] * img.steps[ d ];
		}
		type.updateIndex( index );
	
	}

	@Override
	public void setPosition(int[] pos) {
		int index = 0;
		for ( int d = 0; d < n; ++d )
		{
			position[ d ] = pos[ d ];
			index += pos[ d ] * img.steps[ d ];
		}
		type.updateIndex( index );	
	}

	@Override
	public void setPosition(long[] pos) {
		int index = 0;
		for ( int d = 0; d < n; ++d )
		{
			position[ d ] = (int) pos[ d ];
			index += pos[ d ] * img.steps[ d ];
		}
		type.updateIndex( index );	
	}

	@Override
	public void setPosition(int pos, int d) {
		type.incIndex( ( pos - position[ d ] ) * img.steps[ d ] );
		position[ d ] = pos;	
	}

	@Override
	public void setPosition(long pos, int d) {
		type.incIndex( ( (int)pos - position[ d ] ) * img.steps[ d ] );
		position[ d ] = (int)pos;	
	}

	@Override
	public void fwd(int d) {
		type.incIndex( img.steps[ d ] );
		++position[ d ];
	}

	@Override
	public void bck(int d) {
		type.decIndex( img.steps[ d ] );
		--position[ d ];
	}

	@Override
	public void move(int distance, int d)
	{
		type.incIndex( img.steps[ d ] * distance );
		position[ d ] += distance;
	}

	@Override
	public void move(long distance, int d) {
		type.incIndex( img.steps[ d ] * ( int ) distance );
		position[ d ] += distance;	
	}

	@Override
	public void move(Localizable localizable) {
		int index = 0;
		for ( int d = 0; d < n; ++d )
		{
			final int distance = localizable.getIntPosition( d );
			position[ d ] += distance;
			index += distance * img.steps[ d ];
		}
		type.incIndex( index );	
	}

}
