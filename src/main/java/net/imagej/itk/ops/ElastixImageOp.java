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

import java.io.File;

import net.imagej.ops.AbstractOp;

import net.imglib2.type.numeric.RealType;

import org.itk.simple.ElastixImageFilter;
import org.itk.simple.Image;
import org.itk.simple.ParameterMap;
import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * An op that wraps SimpleElastix registration.
 *
 * @author John Bogovic
 * @param <T>
 * @param <S>
 */
@Plugin(type = ElastixImageOp.class, name = ElastixImageOp.NAME )
public class ElastixImageOp<T extends RealType<T>, S extends RealType<S>>
	extends AbstractOp
{
	public static final String NAME = "Elastix";

	@Parameter
	protected Image movingImage;

	@Parameter
	protected Image fixedImage;

	@Parameter
	protected File elastixParameters;

	@Parameter(type = ItemIO.OUTPUT, required = false)
	protected Image output;

	@Override
	public void run() {

		ElastixImageFilter elastix = new ElastixImageFilter();
		elastix.logToConsoleOn();

		elastix.setMovingImage( movingImage );
		elastix.setFixedImage( fixedImage );

		// read parameters from file
		// (Use tischi's stuff in the future to create one )
		ParameterMap params = elastix.readParameterFile( elastixParameters.getAbsolutePath() );
		elastix.setParameterMap( params );

		// run and get the output
		output = elastix.execute();
	}
}
