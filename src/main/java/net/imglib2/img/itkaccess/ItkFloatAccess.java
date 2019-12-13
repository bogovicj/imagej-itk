package net.imglib2.img.itkaccess;

import org.itk.simple.Image;
import org.itk.simple.PixelIDValueEnum;

import net.imglib2.Interval;
import net.imglib2.img.basictypeaccess.FloatAccess;
import net.imglib2.img.itk.AbstractItkAccess;
import net.imglib2.img.itk.ItkImgFactory;
import net.imglib2.util.IntervalIndexer;

public class ItkFloatAccess extends AbstractItkAccess<ItkFloatAccess> implements FloatAccess
{
	public ItkFloatAccess(Image itkImage)
	{
		super( itkImage );
	}

	@Override
	public float getValue(int index)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		return itkImage.getPixelAsFloat( position.getPosition() );
	}

	@Override
	public void setValue(int index, float value)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		itkImage.setPixelAsFloat( position.getPosition(),  value );
	}

	@Override
	public ItkFloatAccess create(Interval interval)
	{
		Image itkImage = new Image( ItkImgFactory.getSize( interval ), PixelIDValueEnum.sitkFloat32 );
		return new ItkFloatAccess( itkImage );
	}

	public ItkFloatAccess wrap( Image img )
	{
		return new ItkFloatAccess( img );
	}
}
