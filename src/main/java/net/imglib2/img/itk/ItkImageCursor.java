package net.imglib2.img.itk;


import net.imglib2.AbstractCursorInt;
import net.imglib2.AbstractLocalizingCursorInt;
import net.imglib2.iterator.LocalizingIntervalIterator;
import net.imglib2.type.NativeType;
import net.imglib2.util.Intervals;

public class ItkImageCursor< T extends NativeType< T > > extends AbstractLocalizingCursorInt< T >
{
	private ItkImageImg<T,?> img;

	protected final T type;

	protected final int[] max;
	
	protected final int lastIndex;

	public ItkImageCursor( final ItkImageImg<T,?> img ) {
		super( img.numDimensions() );
		this.img = img;
		this.lastIndex = (int)Intervals.numElements(img) - 1;

		max = new int[ n ];
		for ( int d = 0; d < n; ++d )
			max[ d ] = ( int ) img.max( d );

		type = img.createLinkedType();
		type.updateContainer( this );
		reset();
	}

	@Override
	public T get() {
		return type;
	}

	@Override
	public void fwd() {
		type.incIndex();

		if ( ++position[ 0 ] <= max[ 0 ] )
		{
			return;
		}
		else
		{
			position[ 0 ] = 0;

			for ( int d = 1; d < n; ++d )
			{
				if ( ++position[ d ] <= max[ d ] )
					break;
				else
					position[ d ] = 0;
			}
			return;
		}

	}

	@Override
	public void reset() {
		type.updateIndex( 0 );
	}

	@Override
	public boolean hasNext() {
		return type.getIndex() < lastIndex;
	}

	@Override
	public ItkImageCursor<T> copy() {
		return new ItkImageCursor<T>( img );
	}

	@Override
	public ItkImageCursor<T> copyCursor() {
		return copy();
	}
}
