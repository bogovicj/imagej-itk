package net.imglib2.img.itkaccess;

import org.itk.simple.Image;
import org.itk.simple.PixelIDValueEnum;

import net.imglib2.Interval;
import net.imglib2.img.basictypeaccess.DoubleAccess;
import net.imglib2.img.itk.AbstractItkAccess;
import net.imglib2.img.itk.ItkImgFactory;
import net.imglib2.util.IntervalIndexer;

public class ItkDoubleAccess extends AbstractItkAccess<ItkDoubleAccess> implements DoubleAccess
{
	public ItkDoubleAccess(Image itkImage)
	{
		super( itkImage );
	}

	@Override
	public double getValue(int index)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		return itkImage.getPixelAsDouble( position.getPosition() );
	}

	@Override
	public void setValue(int index, double value)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		itkImage.setPixelAsDouble( position.getPosition(),  value );
	}

	@Override
	public ItkDoubleAccess create(Interval interval)
	{
		Image itkImage = new Image( ItkImgFactory.getSize( interval ), PixelIDValueEnum.sitkFloat64 );
		return new ItkDoubleAccess( itkImage );
	}

	public ItkDoubleAccess wrap( Image img )
	{
		return new ItkDoubleAccess( img );
	}
}
