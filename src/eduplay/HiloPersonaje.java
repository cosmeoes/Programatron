/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eduplay;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author cosme
 */
public class HiloPersonaje extends Thread{
    String[] movimientos;
    JLabel jugador;
    String volteando="abajo";
    DefaultListModel<String> model;
    JButton btnReiniciar ;
    ImageIcon caminando= new ImageIcon("./src/imagenes/caminando 1000.gif");
    ImageIcon detenido = new ImageIcon("./src/imagenes/caminando.png");
    ImageIcon brincando = new ImageIcon("./src/imagenes/robot2.png");
    ImageIcon volando = new ImageIcon("./src/imagenes/volando.png");
    ImageIcon dudando= new ImageIcon("./src/imagenes/dudando.gif");
    ImageIcon ganando= new ImageIcon("./src/imagenes/ganando.gif");
    ImageIcon pasaste_nivel= new ImageIcon("./src/imagenes/caminafrente.gif");
    String msgGanar= "Ganaste!!";
    public HiloPersonaje(DefaultListModel model, JLabel jugador, JButton btnReiniciar) {
        this.model=model;
        int i=0;
        this.movimientos= new String[model.size()];
        for(Object o: movimientos){
            this.movimientos[i]=(String) o;
            i++;
        }
        this.jugador=jugador;
        this.btnReiniciar=btnReiniciar;
    }

    @Override
    public void run() {
            boolean resultado=false;
            for(int i=0; i<model.size();i++ ){
                String movimiento=model.get(i);
                switch (movimiento){
                    case "Adelante":
                        resultado=adelante();
                        break;
                    case "Brincar":
                        resultado=brincar();
                        break;
                    case "Bajar":
                        resultado=bajar();
                        break;
                    case "Subir":
                        resultado= subir();
                }
                
                if(!resultado){
                    //jugador.setIcon(detenido);
                    btnReiniciar.doClick();
                    break;
                }
                if(Plataforma.estaEnMeta(jugador.getX(), jugador.getY())){
                    //ganar
                    dormir(500);
                    jugador.setVisible(false);
                    prograGame p= Plataforma.prg;
                    int nivel=p.nivel;
                    
                    String mensaje="Ganaste, ¿continuar?";
                    if(nivel>2){
                        mensaje="¿Quieres volver a jugar?";
                        msgGanar="Niveles completados";
                        nivel=0;
                    }
                    int res=JOptionPane.showConfirmDialog(null,getPanel(),mensaje,
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                    if(res==JOptionPane.YES_OPTION){
                        p.nivel=nivel+1;
                    }
                     btnReiniciar.doClick();
                     jugador.setVisible(true);
                    break;
                }
                dormir(500);
            }
            dudar();
            btnReiniciar.doClick();
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("    "+msgGanar);
        ImageIcon image = null;

        label.setIcon(pasaste_nivel);
        panel.add(label);

        return panel;
    }    
    @Override
    public void interrupt() {
        super.interrupt();
        jugador.setIcon(detenido);
    }
    public boolean subir(){
        int x = jugador.getX();
        int y= jugador.getY();
        x+=130;
        y-=55;
        
        if(Plataforma.existeCamino(x+jugador.getWidth()/2, y+jugador.getHeight())){
            jugador.setIcon(brincando);
            for(int i= jugador.getY(); i>y;i--){
                dormir(10);
                jugador.setLocation(jugador.getX(),i);
            }
            jugador.setIcon(volando);
            for(int i= jugador.getX(); i<x; i++){
                dormir(5);
                jugador.setLocation(i,y);
            }
            jugador.setIcon(detenido);
            
            return true;
        }
        dudar();
        return false;
    }
    
    public boolean adelante(){
       int x= jugador.getX();
       int y = jugador.getY();
       x+=125;
       if(Plataforma.existeCamino(x+jugador.getWidth()/2, y+jugador.getHeight())){
           System.out.println("    "+"adelante");
           jugador.setIcon(caminando);
           for(int i=jugador.getX(); i<x;i++){
               dormir(10);
               jugador.setLocation(i, y);
           }
           jugador.setIcon(detenido);
           return true;
        }
       dudar();
       return false;
    }
    public boolean brincar(){
        int x = jugador.getX();
        int y= jugador.getY();
        x+=260;
        if(Plataforma.existeCamino(x, y+jugador.getHeight())){
            jugador.setIcon(brincando);
            int j =jugador.getY();
            int aumento=-1;
            for(int i=jugador.getX();i<x; i++ ){
                dormir(5);
                jugador.setLocation(i,j);
                if(y-j==130){
                    aumento= (1);
                }
                j+=aumento;
            }
              jugador.setIcon(detenido);
            
            return true;
        }
        dudar();
        return false;
        
    }
    
    public boolean bajar(){
        int x= jugador.getX();
        int y= jugador.getY();
        x+=130;
        y+=50;
        if(Plataforma.existeCamino(x, y+jugador.getHeight())){
            jugador.setLocation(x,y);
            return true;
        }
        dudar();
        return false;
    }

    private void dormir(int mil) {
       try{
           Thread.sleep(mil);
       }catch(Exception e){
           e.printStackTrace();
       }
    }

    private void dudar() {
        int x= jugador.getX();
        int y= jugador.getY();
        x+=130;
        jugador.setIcon(dudando);
       
        dormir(1000);
        
        
    }

    
    
}
