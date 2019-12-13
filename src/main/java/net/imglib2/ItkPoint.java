package net.imglib2;

import org.itk.simple.VectorUInt32;

public class ItkPoint extends AbstractLocalizableInt implements Positionable
{
	private final VectorUInt32 itkPosition;
	
	public ItkPoint( int nd )
	{
		super( nd );
		itkPosition = new VectorUInt32( nd );
	}
	
	public ItkPoint( final VectorUInt32 position )
	{
		super( (int)position.size() );
		this.itkPosition = position;
	}
	
	public VectorUInt32 getPosition()
	{
		return itkPosition;
	}

	@Override
	public void fwd(int d) {
		itkPosition.set( d, itkPosition.get(d) + 1 );
	}

	@Override
	public void bck(int d) {
		itkPosition.set( d, itkPosition.get(d) - 1 );
	}

	@Override
	public void move(int distance, int d) {
		itkPosition.set( d, itkPosition.get(d) + distance );
	}

	@Override
	public void move(long distance, int d) {
		itkPosition.set( d, itkPosition.get(d) + distance );
	}

	@Override
	public void move(Localizable distance) {
		for( int d = 0; d < numDimensions(); d++ )
			itkPosition.set( d, itkPosition.get(d) + distance.getLongPosition( d ) );
	}

	@Override
	public void move(int[] distance) {
		for( int d = 0; d < numDimensions(); d++ )
			itkPosition.set( d, itkPosition.get(d) + distance[ d ] );
	}

	@Override
	public void move(long[] distance) {
		for( int d = 0; d < numDimensions(); d++ )
			itkPosition.set( d, itkPosition.get(d) + distance[ d ] );
	}

	@Override
	public void setPosition(Localizable position) {
		for( int d = 0; d < numDimensions(); d++ )
			itkPosition.set( d, position.getLongPosition( d ));
	}

	@Override
	public void setPosition(int[] position) {
		for( int d = 0; d < numDimensions(); d++ )
			itkPosition.set( d, position[ d ]);
	}

	@Override
	public void setPosition(long[] position) {
		for( int d = 0; d < numDimensions(); d++ )
			itkPosition.set( d, position[ d ]);
	}

	@Override
	public void setPosition(int position, int d) {
		itkPosition.set( d, position );
	}

	@Override
	public void setPosition(long position, int d) {
		itkPosition.set( d, position );
	}

}
