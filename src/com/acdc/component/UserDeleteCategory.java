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

import com.acdc.cnoyel.Categories;
import com.acdc.cnoyel.Tools;
import com.hexidec.ekit.EkitCore;
import com.hexidec.ekit.component.JButtonNoFocus;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class UserDeleteCategory extends JDialog implements ActionListener
{
//	private EkitCore parentEkit;
	private final JTextField jtxfInput = new JTextField(32);
	private JComboBox jComboBoxCategories;
	private String categoryName;
	
	public UserDeleteCategory(EkitCore peKit, String title, boolean bModal, JComboBox jComboBoxCategories)
	{		
		super(peKit.getFrame(), title, bModal);
//		parentEkit = peKit;
		this.jComboBoxCategories = jComboBoxCategories;
       	this.categoryName = jComboBoxCategories.getSelectedItem().toString();
		init();
	}

   	public void actionPerformed(ActionEvent e)
   	{
		if(e.getActionCommand().equals("accept"))
		{
			Categories.removeCategory(categoryName);
			jComboBoxCategories.removeItem(categoryName);
			setVisible(false);
		}	
	  	if(e.getActionCommand().equals("cancel"))
		{
			setVisible(false);
		}
	}

	public void init()
	{
	  	Container contentPane = getContentPane();
	  	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	  	setBounds(100,100,400,300);
	  	setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

	  	JPanel centerPanel = new JPanel();
	  	centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	  	
       	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
	  	JLabel categoryLabel = new JLabel("Voulez-vous vraiment supprimer la catégorie " + categoryName.toUpperCase(), SwingConstants.LEFT);
	  	categoryLabel.setBorder(new EmptyBorder(0, 5, 0, 10));
	  	centerPanel.add(categoryLabel);

		JPanel buttonPanel= new JPanel();	  	
//	  	buttonPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		JButton saveButton = new JButton("Oui");
	  	saveButton.setActionCommand("accept");
		saveButton.addActionListener(this);

	  	JButton cancelButton = new JButton("Non");
	  	cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		contentPane.add(centerPanel);
		contentPane.add(buttonPanel);

 		this.pack();
		this.setVisible(true);
   	}
}

