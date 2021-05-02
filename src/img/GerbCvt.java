/*
 	This file is part of Image2Gerb.

    Image2Gerb is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Image2Gerb is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Image2Gerb.  If not, see <http://www.gnu.org/licenses/>.
 */
package img;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GerbCvt {

	Model model;
	
	public GerbCvt(Model model)
	{
		this.model = model;
	}
	
	
	public void promptFileName( ) 
	{
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			model.setImageFile(f);
		}
	}
	
	
	
	public void readImage( ) throws IOException
	{
		BufferedImage image = null;
		
		image = ImageIO.read(model.getImageFile());

		// check image types
		BitmapLayers layers;
		
		if ((image.getType() == BufferedImage.TYPE_BYTE_BINARY)  || (image.getType() == BufferedImage.TYPE_BYTE_INDEXED) || (image.getType() == BufferedImage.TYPE_BYTE_GRAY)) {
				layers = new BitmapLayers(image.getRaster().getNumBands(), image.getWidth(), image.getHeight());
		}				
		
		else {
			throw new IOException("Unsupported file format." + image.toString());
		}

		Raster rast = image.getData();
		
		for (int l = 0; l < rast.getNumBands(); l++)
			for (int x = 0; x < image.getWidth(); x++)
				for (int y = 0; y < image.getHeight(); y++)
				{
					int samp = rast.getSample(x, y, l);
					if (samp != 0) layers.set(x,y,l,true);
					else layers.set(x,y,l,false);
				}
		
		
		model.setImage(image, layers);
	}

	public void promptSaveFileName( )
	{
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			model.setGerberFile(f);
		}
	}
	
	
	public void run( ) {
		
		
		final JFrame frame = new JFrame("Convert Image to Gerber");
		frame.setPreferredSize(new Dimension(400,200));
		
		final JLabel lblSizesX = new JLabel("Mils / Pixel (X):");
		final JLabel lblSizesY = new JLabel("Mils / Pixel (Y): ");
		final JTextField txtSizesX = new JTextField( );
		final JTextField txtSizesY = new JTextField( );
		
		final JLabel lblImageFile = new JLabel("Image File: ");
		final JTextField txtImageFile = new JTextField();
		final JButton btnImageFile = new JButton("Browse");
		
		final JLabel lblGerbFile = new JLabel("Gerber File: ");
		final JTextField txtGerbFile = new JTextField();
		final JButton btnGerbFile = new JButton("Browse");

		final JButton btnConvert = new JButton("Convert");

		txtSizesX.setText("" + model.getMilX());
		txtSizesY.setText("" + model.getMilY());
		
		frame.setLayout(new GridBagLayout());	
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridheight = gc.gridwidth = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = gc.weighty = 1.0;
		

		gc.gridx = 1;
		gc.gridy = 1;
		frame.add(lblSizesX);
		gc.gridx = 2;
		gc.weightx = 3.0;
		
		frame.add(txtSizesX);
		gc.weightx = 1.0;
		
		gc.gridx = 1;
		gc.gridy = 2;
		frame.add(lblSizesY);
		gc.gridx = 2;
		gc.weightx = 3.0;
		frame.add(txtSizesY);
		gc.weightx = 1.0;
		
		
		gc.gridx = 1;
		gc.gridy = 3;
		frame.add(lblImageFile, gc);
		
		gc.gridx = 2;
		gc.gridwidth = 2;
		gc.weightx = 3.0;
		frame.add(txtImageFile, gc);
		
		gc.gridx = 4;
		gc.weightx = 1.0;
		gc.gridwidth = 1;
		frame.add(btnImageFile, gc);
		
		gc.weightx = 1.0;
		gc.gridy = 4;
		gc.gridx = 1;
		frame.add(lblGerbFile, gc);
		
		gc.gridx = 2;
		gc.weightx = 3.0;
		gc.gridwidth = 2;
		frame.add(txtGerbFile, gc);
		gc.weightx = 1.0;
		gc.gridwidth = 1;
		
		gc.gridx = 4;
		frame.add(btnGerbFile, gc);
		
		
		gc.gridx = 3;
		gc.gridy = 5;
		frame.add(btnConvert, gc);
		
		btnImageFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				promptFileName();
				txtImageFile.setText(model.getImageFile().getAbsolutePath());
			}
		});
		
		btnGerbFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				promptSaveFileName();
				txtGerbFile.setText(model.getGerberFile().getAbsolutePath());
			}
			
		});
		
		
		txtImageFile.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				File F = new File(txtImageFile.getText());
				model.setImageFile(F);				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		txtGerbFile.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				File F = new File(txtGerbFile.getText());
				model.setGerberFile(F);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		FocusListener fl = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				try {
					int x = Integer.parseInt(txtSizesX.getText());
					int y = Integer.parseInt(txtSizesY.getText());
					model.setMils(x, y);
					txtSizesX.setText("" + x);
					txtSizesY.setText("" + y);
					
					System.out.println("Set scales: " + model.getMilX() + " " + model.getMilY());
				}
				catch(RuntimeException E)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Invalid number detected.");
				}
			}
			
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		txtSizesX.addFocusListener(fl);
		txtSizesY.addFocusListener(fl);
		
		btnConvert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileOutputStream fos = new FileOutputStream(model.getGerberFile());
					PrintStream ps = new PrintStream(fos);
					
					readImage();
					GerberWriter writer = new GerberWriter(model);
					writer.writeGerber(ps);
					ps.close();
					fos.close();
					
					JOptionPane.showMessageDialog(new JFrame(), "Conversion Completed.");
				}
				catch(IOException E)
				{
					JOptionPane.showMessageDialog(new JFrame(), E.getMessage());
					E.printStackTrace();
				}
			}
			
		});
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(new Dimension(600,400));
	}
	
	
	public static void main(String args[])
	{
			Model model = new Model( );
			GerbCvt reader = new GerbCvt(model);
			reader.run();
			
//			GerbCvt reader = new GerbCvt( model );
//			reader.promptFileName( );
//			//model.setImageFile(new File("/Users/tbriggs/sulogo.bmp"));
//			reader.readImage();
//			
//			reader.promptSaveFileName();
//			
	}
	
}