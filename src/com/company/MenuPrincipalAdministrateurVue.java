package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MenuPrincipalAdministrateurVue {

    private Connection conn = null;
    private ResultSet res = null;
    private PreparedStatement ps = null;
    private JButton supprimer ;
    private JButton ajouter ;
    private JTable table;
    private DefaultTableModel model;
    private ImageIcon format = null;


    public MenuPrincipalAdministrateurVue() {
        this.conn = com.company.ConnexionBD.Connexion();
        JFrame menuPrincipalAdmin = new JFrame("Menu principal");
        menuPrincipalAdmin.setSize(600, 600);
        menuPrincipalAdmin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuPrincipalAdmin.setVisible(true);
        JPanel panelMenuPrincipalVue2= (JPanel) menuPrincipalAdmin.getContentPane();
        menuPrincipalAdmin.setLayout(new GridLayout(2, 1));
        panelMenuPrincipalVue2.add(TableauDeFilm());
        panelMenuPrincipalVue2.add(boutonSupprimerAjouter());

    }

    public JPanel TableauDeFilm(){
        JPanel panelTableauFilm = new JPanel();
        try
        {

            Statement stm = conn.createStatement();
            res = stm.executeQuery("SELECT*FROM films");

            String columns[] = { "id","titre", "genre", "durée" ,"date","nombre de place","prix de la place","image", "heure de séance"};
            Object data[][] = new Object[100][9];

            int i = 0;
            while (res.next()) {
                int id = res.getInt("identifiantfilm");
                String titre = res.getString("titre");
                String genre = res.getString("genre");
                String duree = res.getString("duree");
                String date = res.getString("date");
                byte[] image = res.getBytes("image");
                String place  = res.getString("nbplace");
                String prix  = res.getString("prix");
                String heuredeseance = res.getString("heuredeseance");

                data[i][0] = id;
                data[i][1] = titre;
                data[i][2] = genre;
                data[i][3] = duree;
                data[i][4] = date;
                data[i][5] = image;
                data[i][6] = place;
                data[i][7] = prix;
                data[i][8]= heuredeseance;


                i++;
            }


            model = new DefaultTableModel(data, columns);
            table = new JTable(model);
            table.setShowGrid(true);
            table.setShowVerticalLines(true);
            JScrollPane pane = new JScrollPane(table);

            panelTableauFilm.add(pane);



        } catch(SQLException e) {
            System.out.println("--> Exception : " + e);
        }finally{
            try{

                res.close();
            }catch (Exception e3){
                System.out.println("--> Exception : " + e3);
            }
        }

        return panelTableauFilm;
    }

    public JPanel boutonSupprimerAjouter(){

        JPanel panelBoutonSupprimerAjouter = new JPanel();
        panelBoutonSupprimerAjouter.setLayout(new FlowLayout(FlowLayout.CENTER ,50,100));
        supprimer = new JButton("supprimer");
        panelBoutonSupprimerAjouter.add(supprimer);
        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int row = table.getSelectedRow();
                String selected = model.getValueAt(row, 0).toString();

                if (row >= 0) {

                    try {

                        String sql = "delete from films where identifiantfilm = ?";
                        ps = conn.prepareStatement(sql);
                        ps.setString( 1, selected );
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Le film a été supprimé");
                    }
                    catch (Exception w) {
                        System.out.println("--> Exception : " + w);

                    }
                    model.removeRow(row);

                }
            }


        });
        ajouter = new JButton("ajouter");
        panelBoutonSupprimerAjouter.add(ajouter);
        ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AjoutFilmVue ajoutFilmVue = new AjoutFilmVue();
            }
        });



        return panelBoutonSupprimerAjouter;
    }


    public static void main(String[] args) {
        MenuPrincipalAdministrateurVue menuPrincipalAdministrateur = new MenuPrincipalAdministrateurVue();
    }
}
