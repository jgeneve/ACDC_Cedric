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
import com.hexidec.ekit.EkitCore;
import com.hexidec.ekit.component.JButtonNoFocus;
import com.hexidec.ekit.component.MutableFilter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class UserInputChangeGitConfig extends JDialog implements ActionListener
{
//	private EkitCore parentEkit;
	private String inputTextLocalRepo = null;
	private final JTextField jtxfInputLocalRep = new JTextField(32);
	private JComboBox<String> jComboBoxCategory;
	private EkitCore ekitCore;
	
	public UserInputChangeGitConfig(EkitCore peKit, String title, boolean bModal, JComboBox<String> jComboBox)
	{		
		super(peKit.getFrame(), title, bModal);
		this.ekitCore = peKit;
		this.jComboBoxCategory = jComboBox;
//		parentEkit = peKit;
		init();
	}

   	public void actionPerformed(ActionEvent e)
   	{
		if (e.getActionCommand().equals("accept"))
		{
			inputTextLocalRepo = jtxfInputLocalRep.getText();
			if (!inputTextLocalRepo.isEmpty()) {
				PropertiesAccess access = PropertiesAccess.getInstance();
				access.changeLocalRepository(jtxfInputLocalRep.getText());
				List<String> categories = Categories.getCategories();
				
				jComboBoxCategory.setModel(new DefaultComboBoxModel(categories.toArray()));
				
				if (categories.isEmpty()) {
					List<String> errors = new ArrayList<>();
					errors.add("Impossible de charger les catégories.");
					errors.add("Veuillez vérifier le chemin vers le dossier web local.");
					errors.add("Pour cela se rendre dans Fichier > Changer le chemin local");
					new DialogInvalidForm(ekitCore, "Chemin local invalide", false, errors);
				}
				
			}
			setVisible(false);
		}
		if (e.getActionCommand().equals("edit_local")) {
			String localPath = getFileFromChooser(".", JFileChooser.OPEN_DIALOG, "Chemin vers le dossier web local").getPath();
			jtxfInputLocalRep.setText(localPath);
		}
	  	if (e.getActionCommand().equals("cancel"))
		{
			inputTextLocalRepo = null;
			setVisible(false);
		}
	}

	public void init()
	{
	  	Container contentPane = getContentPane();
	  	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	  	setBounds(100,100,400,300);
	  	setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	  	
	  	JPanel topPanel = new JPanel();
	  	topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	  	
	  	topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
	  	
	  	JLabel localRepoLabel = new JLabel("Chemin vers le dossier local du site :", SwingConstants.LEFT);
	  	localRepoLabel.setBorder(new EmptyBorder(0, 5, 0, 10));
	  	localRepoLabel.setPreferredSize(new Dimension(230, 25));
	  	
	  	JButtonNoFocus jButtonNoFocusEditLocalRepo = new JButtonNoFocus(new ImageIcon("./src/resources/edit.png"));  	
	  	jButtonNoFocusEditLocalRepo.setActionCommand("edit_local");
	  	jButtonNoFocusEditLocalRepo.addActionListener(this);
	  	
	  	System.out.println(PropertiesAccess.getInstance().getLocalRepository());
	  	
	  	jtxfInputLocalRep.setText(PropertiesAccess.getInstance().getLocalRepository());
	  	jtxfInputLocalRep.setEditable(false);
	  	
	  	topPanel.add(localRepoLabel);
	  	topPanel.add(jtxfInputLocalRep);
	  	topPanel.add(jButtonNoFocusEditLocalRepo);

		JPanel buttonPanel= new JPanel();	  	
//	  	buttonPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

		JButton saveButton = new JButton("Accepter");
	  	saveButton.setActionCommand("accept");
		saveButton.addActionListener(this);

	  	JButton cancelButton = new JButton("Annuler");
	  	cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		contentPane.add(topPanel);
		contentPane.add(buttonPanel);

 		this.pack();
		this.setVisible(true);
   	}
	
	private File getFileFromChooser(String startDir, int dialogType, String desc)
	{
		JFileChooser jfileDialog = new JFileChooser(startDir);
		jfileDialog.setDialogType(dialogType);
		jfileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int optionSelected = JFileChooser.CANCEL_OPTION;
		if(dialogType == JFileChooser.OPEN_DIALOG)
		{
			optionSelected = jfileDialog.showOpenDialog(this);
		}
		else if(dialogType == JFileChooser.SAVE_DIALOG)
		{
			optionSelected = jfileDialog.showSaveDialog(this);
		}
		else // default to an OPEN_DIALOG
		{
			optionSelected = jfileDialog.showOpenDialog(this);
		}
		if(optionSelected == JFileChooser.APPROVE_OPTION)
		{
			return jfileDialog.getSelectedFile();
		}
		return (File)null;
	}
}

