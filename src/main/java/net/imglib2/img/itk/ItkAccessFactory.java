package net.imglib2.img.itk;


import net.imglib2.img.itkaccess.ItkByteAccess;
import net.imglib2.img.itkaccess.ItkDoubleAccess;
import net.imglib2.img.itkaccess.ItkFloatAccess;
import net.imglib2.img.itkaccess.ItkIntAccess;
import net.imglib2.img.itkaccess.ItkLongAccess;
import net.imglib2.img.itkaccess.ItkShortAccess;
import net.imglib2.type.NativeTypeFactory;
import net.imglib2.type.PrimitiveType;

public class ItkAccessFactory
{
	public static < A extends AbstractItkAccess<A> > A get(
			final NativeTypeFactory< ?, ? super A > typeFactory )
	{
		return get( typeFactory.getPrimitiveType() );
	}

	@SuppressWarnings("unchecked")
	public static <A extends AbstractItkAccess<A> > A get(
			final PrimitiveType primitiveType )
	{
		switch ( primitiveType )
		{
		case BYTE:
			return (A) new ItkByteAccess( null );
		case DOUBLE:
			return (A) new ItkDoubleAccess( null );
		case FLOAT:
			return (A) new ItkFloatAccess( null );
		case INT:
			return (A) new ItkIntAccess( null );
		case LONG:
			return (A) new ItkLongAccess( null );
		case SHORT:
			return (A) new ItkShortAccess( null );
		default:
			throw new IllegalArgumentException();
		}
	}

}
