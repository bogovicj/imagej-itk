package net.imglib2.img.itk;

import org.itk.simple.Image;
import org.itk.simple.VectorUInt32;

import net.imglib2.FinalInterval;
import net.imglib2.Interval;
import net.imglib2.ItkPoint;
import net.imglib2.Positionable;
import net.imglib2.img.basictypeaccess.ByteAccess;
import net.imglib2.util.Fraction;
import net.imglib2.util.IntervalIndexer;

public abstract class AbstractItkAccess<A extends AbstractItkAccess<A>>
{
	protected Image itkImage;
	
	final protected ItkPoint position;
	
	final protected Interval interval;
	
	public AbstractItkAccess( final Image itkImage) {
		this.itkImage = itkImage;

		if( itkImage != null )
		{
			position = new ItkPoint( (int) itkImage.getDimension() );
			long[] dim  = new long[ (int) itkImage.getDimension() ];
			VectorUInt32 sz = itkImage.getSize();
			for( int d = 0; d < dim.length; d++ )
				dim[ d ] = sz.get( d );

			interval = new FinalInterval( dim );
		}
		else
		{
			position = null;
			interval = null;
		}
	}

	public abstract A create( Interval interval );

	public abstract A wrap( Image itkImage );

	public A create( long[] dimensions )
	{
		return create( new FinalInterval( dimensions ));
	}
	
	public Fraction getEntitiesPerPixel()
	{
		return new Fraction( itkImage.getNumberOfComponentsPerPixel(), 1 );
	}
	
	public Image getImage()
	{
		return itkImage;
	}
}
