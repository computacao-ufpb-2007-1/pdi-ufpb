/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.pdi.gui.menus;

import br.ufpb.di.pdi.gui.MainWindow;
import javax.swing.JMenu;

/**
 *
 * @author thiago
 */
public class MainMenu extends JMenu {

    public MainMenu (MainWindow father) {
        add(new FileMenu(father));
        addSeparator();
        add(new ExibitionMenu(father));
        addSeparator();
        add(new FilterMenu(father));
    }

}
