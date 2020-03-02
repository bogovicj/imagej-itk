/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2015 - 2016 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imagej.itk.ops;

import net.imagej.Dataset;
import net.imagej.ops.AbstractOp;
import net.imglib2.type.numeric.RealType;

import org.itk.simple.Image;
import org.itk.simple.VectorDouble;
import org.scijava.ItemIO;
import org.scijava.Priority;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * An op that wraps the itk implementation of Recursive Gaussian Filter
 *
 * @author Brian Northan
 * @author John Bogovic
 * @param <T>
 * @param <S>
 */
@Plugin(type = RecursiveGaussian.class, name = RecursiveGaussian.NAME,
	priority = Priority.HIGH + 50 )
public class RecursiveGaussianImageOp<T extends RealType<T>, S extends RealType<S>>
	extends AbstractOp
{

	@Parameter
	UIService ui;

	@Parameter
	protected Image itkImage;

	@Parameter
	protected double sigmaX = 3.0;

	@Parameter
	protected double sigmaY = 3.0;

	@Parameter
	protected double sigmaZ = 3.0;

	@Parameter(type = ItemIO.OUTPUT, required = false)
	protected Image output;

	@Override
	public void run() {

		final org.itk.simple.SmoothingRecursiveGaussianImageFilter itkGauss =
			new org.itk.simple.SmoothingRecursiveGaussianImageFilter();

		long nd = itkImage.getDimension();
		VectorDouble sigmaVec;
		if( nd > 3 )
		{
			// throw exception
			ui.showDialog( "This plugin works on data of less than five dimensions" );
			return;
		}
		else
		{
			sigmaVec = new VectorDouble( nd );
			if( nd > 0 ){  sigmaVec.set( 0, sigmaX ); };
			if( nd > 1 ){  sigmaVec.set( 1, sigmaY ); };
			if( nd > 2 ){  sigmaVec.set( 2, sigmaZ ); };
		}

		// call itk rl using simple itk wrapper
		output = itkGauss.execute(itkImage, sigmaVec, false );
	}
}
