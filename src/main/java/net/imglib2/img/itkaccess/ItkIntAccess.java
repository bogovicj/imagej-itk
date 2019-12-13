package net.imglib2.img.itkaccess;

import org.itk.simple.Image;
import org.itk.simple.PixelIDValueEnum;

import net.imglib2.Interval;
import net.imglib2.img.basictypeaccess.IntAccess;
import net.imglib2.img.itk.AbstractItkAccess;
import net.imglib2.img.itk.ItkImgFactory;
import net.imglib2.util.IntervalIndexer;

public class ItkIntAccess extends AbstractItkAccess<ItkIntAccess> implements IntAccess
{
	public ItkIntAccess(Image itkImage)
	{
		super( itkImage );
	}

	@Override
	public int getValue(int index)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		return itkImage.getPixelAsInt32( position.getPosition() );
	}

	@Override
	public void setValue(int index, int value)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		itkImage.setPixelAsInt32( position.getPosition(),  value );
	}

	@Override
	public ItkIntAccess create(Interval interval)
	{
		Image itkImage = new Image( ItkImgFactory.getSize( interval ), PixelIDValueEnum.sitkInt32 );
		return new ItkIntAccess( itkImage );
	}

	public ItkIntAccess wrap( Image img )
	{
		return new ItkIntAccess( img );
	}
}
