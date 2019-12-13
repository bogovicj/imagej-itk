package net.imglib2.img.itkaccess;

import org.itk.simple.Image;
import org.itk.simple.PixelIDValueEnum;

import net.imglib2.Interval;
import net.imglib2.img.basictypeaccess.ShortAccess;
import net.imglib2.img.itk.AbstractItkAccess;
import net.imglib2.img.itk.ItkImgFactory;
import net.imglib2.util.IntervalIndexer;

public class ItkShortAccess extends AbstractItkAccess<ItkShortAccess> implements ShortAccess
{
	public ItkShortAccess(Image itkImage)
	{
		super( itkImage );
	}

	@Override
	public short getValue(int index)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		return itkImage.getPixelAsInt16( position.getPosition() );
	}

	@Override
	public void setValue(int index, short value)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		itkImage.setPixelAsInt16( position.getPosition(),  value );
	}

	@Override
	public ItkShortAccess create(Interval interval)
	{
		Image itkImage = new Image( ItkImgFactory.getSize( interval ), PixelIDValueEnum.sitkInt16 );
		return new ItkShortAccess( itkImage );
	}

	public ItkShortAccess wrap( Image img )
	{
		return new ItkShortAccess( img );
	}
}
