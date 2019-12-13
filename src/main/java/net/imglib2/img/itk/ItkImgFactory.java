package net.imglib2.img.itk;

import org.itk.simple.Image;
import org.itk.simple.VectorUInt32;

import net.imglib2.Dimensions;
import net.imglib2.Interval;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.NativeImg;
import net.imglib2.type.NativeType;
import net.imglib2.type.NativeTypeFactory;

public class ItkImgFactory <T extends NativeType<T>> extends ImgFactory<T>
{
	public ItkImgFactory( final T type )
	{
		super( type );
	}

	public ItkImgFactory( final Image itkImage )
	{
		super( ItkImageImg.getSuitableType( itkImage ));
	}

	@Override
	public ItkImageImg<T,?> create(long... dimensions) {
		return create( dimensions, super.type() );
	}

	@SuppressWarnings("unchecked")
	@Override
	public ItkImageImg<T,?> create(long[] dim, T type)
	{
		return create( dim, type, ((NativeTypeFactory)type().getNativeTypeFactory()));
	}
	
	public ItkImageImg< T,? > create( final Dimensions dimensions )
	{
		final long[] size = new long[ dimensions.numDimensions() ];
		dimensions.dimensions( size );

		return create( size );
	}

	private <A extends AbstractItkAccess<A>> ItkImageImg<T,A> create(
			final long[] dimensions,
			final T type,
			final NativeTypeFactory< T, A > typeFactory )
	{
		A access = ItkAccessFactory.get( typeFactory ).create(dimensions);
		ItkImageImg<T,A> img = new ItkImageImg<T,A>( access, dimensions, type );
		img.setLinkedType( typeFactory.createLinkedType( (NativeImg<T, ? extends A>) img ) );

		return img;
	}
	
	public static VectorUInt32 getSize( Interval dimensions )
	{
		VectorUInt32 size = new VectorUInt32( dimensions.numDimensions() );
		for( int d = 0; d < dimensions.numDimensions(); d++ )
			size.set(d, dimensions.dimension( d ));

		return size;
	}

	public static VectorUInt32 getSize( long[] dimensions )
	{
		VectorUInt32 size = new VectorUInt32( dimensions.length );
		for( int d = 0; d < dimensions.length; d++ )
			size.set(d, dimensions[ d ]);

		return size;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <S> ImgFactory<S> imgFactory( final S type ) throws IncompatibleTypeException
	{
		if ( type instanceof NativeType )
		{
			return new ItkImgFactory( (NativeType)type  );
		}
		throw new IncompatibleTypeException(this, "Type " + type.getClass().getName() + " not supported");
	}

}
