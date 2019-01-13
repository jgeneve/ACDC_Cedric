/*
GNU Lesser General Public License

UserInputAnchorDialog
Copyright (C) 2000 Howard Kistler & other contributors

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package com.acdc.component;

import com.hexidec.ekit.EkitCore;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class DialogWaitingScreen extends JDialog
{
	private String message;
	
	public DialogWaitingScreen(EkitCore peKit, String title, String message, boolean bModal)
	{		
		super(peKit.getFrame(), title, bModal);
		this.message = message;
		init();
	}

	public void init()
	{
	  	Container contentPane = getContentPane();
	  	setBounds(100,100,400,300);
	  	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	  	setResizable(false);
	  	
       	JLabel jLabelMessage = new JLabel(message);
       	jLabelMessage.setBorder(new EmptyBorder(20, 20, 20, 20));
       	contentPane.add(jLabelMessage);

 		this.pack();
		this.setVisible(true);
   	}
}

