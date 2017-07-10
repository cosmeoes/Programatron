/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eduplay;

import java.awt.Component;
import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author cosme
 */
public class Plataforma {
    public static JPanel p=null;
    public static JLabel background=null;
    public static prograGame prg= null;
    public static JButton btnGanaste=null;
    public static JLabel lblGanaste=null;
    public static boolean existeCamino(int x ,int y){
        Component com=p.getComponentAt(x, y);
        System.out.println(com);
        if(com instanceof JButton){
            return true;
        }
        return false;
    }
    public static boolean estaEnMeta(int x,int y){
       Component com=p.getComponentAt(x, y);
        System.out.println("ganste "+com);
        if(com instanceof JToggleButton){
            return true;
        }
        return false;
    }
    
}
