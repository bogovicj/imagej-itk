package net.imglib2.img.itkaccess;

import org.itk.simple.Image;
import org.itk.simple.PixelIDValueEnum;

import net.imglib2.Interval;
import net.imglib2.img.basictypeaccess.ByteAccess;
import net.imglib2.img.itk.AbstractItkAccess;
import net.imglib2.img.itk.ItkImgFactory;
import net.imglib2.util.IntervalIndexer;

public class ItkByteAccess extends AbstractItkAccess<ItkByteAccess> implements ByteAccess
{
	public ItkByteAccess(Image itkImage)
	{
		super( itkImage );
	}

	@Override
	public byte getValue(int index)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		// TODO is this right?
		return (byte)itkImage.getPixelAsUInt8( position.getPosition() );
	}

	@Override
	public void setValue(int index, byte value)
	{
		IntervalIndexer.indexToPositionForInterval(index, interval, position);
		itkImage.setPixelAsUInt8( position.getPosition(),  (short)value );
	}

	@Override
	public ItkByteAccess create(Interval interval)
	{
		Image itkImage = new Image( ItkImgFactory.getSize( interval ), PixelIDValueEnum.sitkUInt8 );
		return new ItkByteAccess( itkImage );
	}
	
	public ItkByteAccess wrap( Image img )
	{
		return new ItkByteAccess( img );
	}

}
