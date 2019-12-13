package net.imglib2.img.itk;

import org.itk.simple.Image;
import org.itk.simple.PixelIDValueEnum;
import org.itk.simple.VectorUInt32;

import net.imglib2.img.AbstractNativeImg;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.NativeImg;
import net.imglib2.type.NativeType;
import net.imglib2.type.NativeTypeFactory;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.util.Fraction;
import net.imglib2.util.IntervalIndexer;

public class ItkImageImg< T extends NativeType< T >, A extends AbstractItkAccess<A> > extends AbstractNativeImg< T, A >
{
	final protected A access;
	
	final protected T type;
	
	final protected long[] dimensions;

	final protected int[] dims;
	
	final protected int[] steps;

	public ItkImageImg( final A access, final long[] dimensions, final T type )
	{
		super( dimensions, access.getEntitiesPerPixel() );
		this.access = access;
		this.type = type.copy();

		// TODO copy dimensions
		this.dimensions = dimensions;
		this.dims = new int[ n ];
		for ( int d = 0; d < n; ++d )
			this.dims[ d ] = ( int ) dimensions[ d ];

		this.steps = new int[ n ];
		IntervalIndexer.createAllocationSteps( this.dims, this.steps );
	}
	
	public static long[] getSize( final Image itkImage )
	{
		long[] size = new long[ (int)itkImage.getDimension() ];
		final VectorUInt32 itkDimensions = itkImage.getSize();
		for( int d = 0; d < size.length; d++ )
		{
			size[ d ] = itkDimensions.get( d );
		}
		return size;
	}
	
	public Image getImage()
	{
		return access.getImage();
	}

	@SuppressWarnings("unchecked")
	public static <T extends NativeType<T>> T getSuitableType( final Image itkImage )
	{
		String pixelIdString = itkImage.getPixelIDTypeAsString();

		// TODO finish this
		switch( pixelIdString )
		{
		case "8-bit unsigned integer":
			return (T) new UnsignedByteType();
		case "16-bit unsigned integer":
			return (T) new UnsignedShortType();
		case "32-bit float":
			return (T) new FloatType();
		case "64-bit float":
			return (T) new DoubleType();
		default:
			System.err.println("Type " + pixelIdString + " not yet supported.");
		}

		return null;
	}
	
	public static Fraction getEntitiesPerPixel( final Image itkImage )
	{
		return new Fraction( itkImage.getNumberOfComponentsPerPixel(), 1 );
	}

	public static <T> PixelIDValueEnum getSuitableType( T type )
	{
		String className = type.getClass().getName();
		System.out.println( "getSuitableType " + className );
		switch( className )
		{
		case "net.imglib2.type.numeric.integer.UnsignedByteType":
			return PixelIDValueEnum.sitkUInt8;
		case "net.imglib2.type.numeric.integer.ByteType":
			return PixelIDValueEnum.sitkInt8;
		case "net.imglib2.type.numeric.integer.UnsignedShortType":
			return PixelIDValueEnum.sitkUInt16;
		case "net.imglib2.type.numeric.integer.ShortType":
			return PixelIDValueEnum.sitkInt16;
		case "net.imglib2.type.numeric.integer.UnsignedIntType":
			return PixelIDValueEnum.sitkUInt32;
		case "net.imglib2.type.numeric.integer.IntType":
			return PixelIDValueEnum.sitkInt32;
		case "net.imglib2.type.numeric.integer.LongType":
			return PixelIDValueEnum.sitkInt64;
		case "net.imglib2.type.numeric.real.FloatType":
			return PixelIDValueEnum.sitkFloat32;
		case "net.imglib2.type.numeric.real.DoubleType":
			return PixelIDValueEnum.sitkFloat64;
		default:
			System.err.println("Type " + className + " not yet supported.");
		}
		return null;
	}

	@Override
	public ImgFactory<T> factory() {
		return new ItkImgFactory< T >( type );
	}

	@Override
	public ItkImageImg<T,A> copy() {
		return new ItkImageImg<T,A>( access, dimensions, type );
	}

	@Override
	public ItkImageRandomAccess<T> randomAccess() {
		return new ItkImageRandomAccess<T>( this );
	}

	@Override
	public ItkImageCursor<T> cursor() {
		return localizingCursor();
	}

	@Override
	public ItkImageCursor<T> localizingCursor() {
		return new ItkImageCursor<T>( this );
	}

	@Override
	public Object iterationOrder() {
		// TODO fix this
		return null;
	}

	@Override
	public A update(Object updater) {
		return access;
	}

	@SuppressWarnings("unchecked")
	public static <T extends NativeType<T>, A extends AbstractItkAccess<A>> ItkImageImg<T,A> wrap(
			Image itkImage )
	{
		T t = ItkImageImg.getSuitableType( itkImage );
		NativeTypeFactory<T, A> tf = (NativeTypeFactory<T, A>) t.getNativeTypeFactory();
		A access = ItkAccessFactory.get( tf ).wrap( itkImage );
		ItkImageImg<T,A> img = new ItkImageImg<T, A>( access, getSize( itkImage ), t);
		img.setLinkedType( tf.createLinkedType( (NativeImg<T, ? extends A>) img ) );
		return img;
	}

}
