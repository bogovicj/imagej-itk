
package net.imagej.itk.commands;

import org.itk.simple.Image;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.Dataset;
import net.imagej.DatasetService;
import net.imagej.itk.ImageJSimpleItkService;
import net.imagej.itk.ops.RecursiveGaussian;
import net.imagej.ops.OpService;
import net.imglib2.img.Img;
import net.imglib2.meta.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

/**
 * A command with parameters and a menu item. The command will be added to the
 * ImageJ menu (under "Plugins", "ITK").
 * 
 * @author bnorthan
 * @param <T>
 */
@Plugin(type = Command.class, menuPath = "Plugins>ITK>ITK Gaussian")
public class ItkRecursiveGaussianImageCommand<T extends RealType<T> & NativeType<T>>
	implements Command
{

	@Parameter
	private ImageJSimpleItkService ijitkService;

	@Parameter
	protected DatasetService data;

	@Parameter
	protected OpService ops;

	@Parameter
	protected Image input;

	@Parameter
	protected float sigma = 3.0f;

	@Parameter(type = ItemIO.OUTPUT)
	protected Dataset output;

	public void run() {

		// call the op
		Image image = (Image) ops.run(RecursiveGaussian.NAME, input, sigma);

		output = ijitkService.getDataset(image);
	}

}
