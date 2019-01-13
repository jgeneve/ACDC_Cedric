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
import com.acdc.cnoyel.PropertiesAccess;
import com.acdc.cnoyel.Tools;
import com.hexidec.ekit.Ekit;
import com.hexidec.ekit.EkitCore;
import com.hexidec.ekit.component.JButtonNoFocus;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

public class DialogValidateDemo extends JDialog implements ActionListener
{
	private final JTextField jtxfInput = new JTextField(32);
	private EkitCore peKit;
	
	public DialogValidateDemo(EkitCore peKit, String title, boolean bModal)
	{		
		super(peKit.getFrame(), title, bModal);
		this.peKit = peKit;
		init();
	}

   	public void actionPerformed(ActionEvent e)
   	{
		if(e.getActionCommand().equals("accept"))
		{
			Tools.gitCommitAndPush(PropertiesAccess.getInstance().getLocalRepository());
			setVisible(false);
			
			List<String> messages = new ArrayList<>();
			messages.add("Fichier upload avec succès.");
			new DialogInvalidForm(peKit, "", true, messages);
			Tools.killJekyll();
			Ekit.cleanInputs();
		}	
	  	if(e.getActionCommand().equals("cancel"))
		{
			setVisible(false);
			Tools.killJekyll();
			//Tools.executeCmd("git checkout .", PropertiesAccess.getInstance().getLocalRepository());
			Tools.executeCmd("git clean -f", PropertiesAccess.getInstance().getLocalRepository());
		}
	}

	public void init()
	{
	  	Container contentPane = getContentPane();
	  	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	  	setBounds(100,100,400,300);
	  	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	  	setResizable(false);

	  	JPanel centerPanel = new JPanel();
	  	centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	  	
       	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	  	JLabel waitLabel = new JLabel("L'aperçu va s'afficher dans un instant, veuillez patienter ...", SwingConstants.LEFT);
	  	waitLabel.setBorder(new EmptyBorder(0, 5, 0, 10));
	  	JLabel validateLabel = new JLabel("Voulez-vous valider l'aperçu et upload le nouveau fichier sur Git ?", SwingConstants.LEFT);
	  	validateLabel.setBorder(new EmptyBorder(0, 5, 0, 10));
	  	centerPanel.add(waitLabel);
	  	centerPanel.add(validateLabel);

		JPanel buttonPanel= new JPanel();	  	
//	  	buttonPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		JButton saveButton = new JButton("Valider");
	  	saveButton.setActionCommand("accept");
		saveButton.addActionListener(this);

	  	JButton cancelButton = new JButton("Annuler");
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

