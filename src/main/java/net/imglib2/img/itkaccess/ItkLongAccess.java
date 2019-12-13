package net.imglib2.img.itkaccess;

import org.itk.simple.Image;
import org.itk.simple.PixelIDValueEnum;

import net.imglib2.Interval;
import net.imglib2.img.basictypeaccess.LongAccess;
import net.imglib2.img.itk.AbstractItkAccess;
import net.imglib2.img.itk.ItkImgFactory;
import net.imglib2.util.IntervalIndexer;

public class ItkLongAccess extends AbstractItkAccess<ItkLongAccess> implements LongAccess
{
	public ItkLongAccess(Image itkImage)
	{
		super( itkImage );
	}

	@Override
	public long getValue(int index)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		return (long)itkImage.getPixelAsInt64( position.getPosition() );
	}

	@Override
	public void setValue(int index, long value)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		itkImage.setPixelAsInt64( position.getPosition(),  (int) value );
	}

	@Override
	public ItkLongAccess create(Interval interval)
	{
		Image itkImage = new Image( ItkImgFactory.getSize( interval ), PixelIDValueEnum.sitkInt64 );
		return new ItkLongAccess( itkImage );
	}

	public ItkLongAccess wrap( Image img )
	{
		return new ItkLongAccess( img );
	}
}
