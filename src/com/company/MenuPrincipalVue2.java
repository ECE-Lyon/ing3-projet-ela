package com.company;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class MenuPrincipalVue2 {
    private Connection conn = null;
    private ResultSet res = null;
    private PreparedStatement ps = null;
    private JLabel champGenre;
    private JLabel champTitre;
    private JLabel champDuree;
    private JLabel champHeure;
    private JLabel champDate;
    private JLabel champPrix;
    private JLabel champPlace;
    private static String ligne;
    private JTable table;
    private JLabel photo;
    private JTextField champsNombreDeTicket;
    private int nombre;
    private DefaultTableModel model;


    public MenuPrincipalVue2() {
        this.conn = com.company.ConnexionBD.Connexion();
        JFrame f = new JFrame("Menu principal");
        f.setSize(1000, 600);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
        JPanel panelMenuPrincipalVue2= (JPanel) f.getContentPane();
        f.setLayout(new GridLayout(1, 2));
        panelMenuPrincipalVue2.add(TableauDeFilm());
        panelMenuPrincipalVue2.add(InfoPlusPhoto());



    }


    public JPanel TableauDeFilm(){
        JPanel panel = new JPanel();
        try
        {

            String query = "SELECT * FROM films";

            Statement stm = conn.createStatement();
            res = stm.executeQuery(query);

            String columns[] = { "titre", "genre", "durée" ,"date de sortie","image","nombre de place ","prix","heure de la seance"};
            Object data[][] = new Object[100][8];

            int i = 0;
            while (res.next()) {
                String titre = res.getString("titre");
                String genre = res.getString("genre");
                String duree = res.getString("duree");
                String date = res.getString("date");
                byte[] image = res.getBytes("image");
                String place  = res.getString("nbplace");
                String prix  = res.getString("prix");
                String heure  = res.getString("heuredeseance");

                data[i][0] = titre;
                data[i][1] = genre;
                data[i][2] = duree;
                data[i][3] = date;
                data[i][4] = image;
                data[i][5] = place;
                data[i][6] = prix;
                data[i][7] = heure;
                i++;
            }

            model = new DefaultTableModel(data, columns);
            table = new JTable(model);
            table.setShowGrid(true);
            table.setShowVerticalLines(true);
            JScrollPane pane = new JScrollPane(table);
            table.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Deplace();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });




            panel.add(pane);



        } catch(SQLException e) {
            System.out.println("--> Exception : " + e);
        }

        return panel;
    }
    public JPanel InfoPlusPhoto(){
        JPanel panelInfoPlusPhoto = new JPanel(new GridLayout(2,1));
        panelInfoPlusPhoto.add(photo());
        panelInfoPlusPhoto.add(InformationFilm());
        return panelInfoPlusPhoto;

    }
    public JPanel InformationFilm(){
        JPanel panelInformationFilm = new JPanel();
        panelInformationFilm.setLayout(new GridLayout(9,1));

        panelInformationFilm.add(titreFilm());
        panelInformationFilm.add(genreFilm());
        panelInformationFilm.add(dateFilm());
        panelInformationFilm.add(dureeFilm());
        panelInformationFilm.add(heureFilm());
        panelInformationFilm.add(placeFilm());
        panelInformationFilm.add(prixFilm());
        panelInformationFilm.add(nombreDeTicket());
        panelInformationFilm.add(reservation());

        return panelInformationFilm;

    }
    public JPanel titreFilm()
    {
        JPanel panelTitreFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel titre = new JLabel("Titre:");
        panelTitreFilm.add(titre);
        champTitre = new JLabel();
        panelTitreFilm.add(champTitre);
        return  panelTitreFilm ;

    }
    public JPanel placeFilm()
    {
        JPanel panelPlaceFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel place = new JLabel("Place:");
        panelPlaceFilm.add(place);
        champPlace= new JLabel();
        panelPlaceFilm.add(champPlace);
        return  panelPlaceFilm ;

    }
    public JPanel prixFilm()
    {
        JPanel panelPlaceFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel prix = new JLabel("Prix:");
        panelPlaceFilm.add(prix);
        champPrix= new JLabel();
        panelPlaceFilm.add(champPrix);
        return  panelPlaceFilm ;

    }

    public JPanel genreFilm()
    {
        JPanel panelGenreFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel genre = new JLabel("Genre:");
        panelGenreFilm.add(genre);
        champGenre = new JLabel();
        panelGenreFilm.add(champGenre);
        return  panelGenreFilm ;

    }
    public JPanel dureeFilm()
    {
        JPanel panelDureeFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel duree = new JLabel("Durée:");
        panelDureeFilm.add(duree);
        champDuree = new JLabel();
        panelDureeFilm.add(champDuree);
        return  panelDureeFilm ;

    }
    public JPanel dateFilm() {
        JPanel panelDateFilm = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 5));
        JLabel date = new JLabel("Date:");
        panelDateFilm.add(date);
        champDate = new JLabel();
        panelDateFilm.add(champDate);
        return panelDateFilm;
    }
    public JPanel heureFilm() {
        JPanel panelHeureFilm = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 5));
        JLabel heure = new JLabel("Heure de la séance:");
        panelHeureFilm.add(heure);
        champHeure = new JLabel();
        panelHeureFilm.add(champHeure);
        return panelHeureFilm;
    }

    public JPanel photo()
    {
        JPanel panelPhoto = new JPanel();
        photo = new JLabel();
        panelPhoto.add(photo);
        return  panelPhoto ;

    }

    public JPanel reservation(){
        JPanel panelReservation = new JPanel();
        JButton acheter = new JButton("réservation");
        panelReservation.add(acheter);
        acheter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row= table.getSelectedRow();
                int nbplace= Integer.parseInt(champPlace.getText());
                int nbplacereservees= Integer.parseInt(champsNombreDeTicket.getText());


                if (nbplace>= nbplacereservees ){
                    if (row>=0){
                        try {
                            String requet= "update films set nbplace='"+ (nbplace - nbplacereservees)+"'  where titre = ? ";
                            ps = conn.prepareStatement(requet);
                            ps.setString(1,(String) model.getValueAt(row,0));
                            ps.executeUpdate();
                            ps.close();
                            JOptionPane.showMessageDialog(null,"vous avez bien réservé "+ nbplacereservees +" place(s) prenez en photo votre écran comme preuve de réservation");
                        } catch (Exception e10){
                            System.out.println("--> Exception : " + e10);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Il n'y a plus assez de place");
                }
            }
        });

        return panelReservation;
    }

    public JPanel nombreDeTicket(){
        JPanel panelNombreDeTicket = new JPanel(new FlowLayout(FlowLayout.CENTER , 20,5));
        champsNombreDeTicket = new JTextField(20);
        panelNombreDeTicket.add(champsNombreDeTicket);
        JButton valider = new JButton("Valider");
        panelNombreDeTicket.add(valider);
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                nombre = Integer.parseInt(champsNombreDeTicket.getText());
                System.out.println(nombre);
                if(nombre > 0) {
                    float total = nombre * Integer.parseInt(champPrix.getText()) ;
                    JOptionPane.showMessageDialog(null,"le prix est de : "+total+"€ que vous réglerez au cinéma");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez pas indiqué combien de ticket vous voulez");
                }

            }
        });
        return panelNombreDeTicket;
    }

    public void Deplace (){
        try{
            int row = table.getSelectedRow();
            this.ligne = (table.getModel().getValueAt(row,0).toString());
            String requet ="select * from films where titre ='"+ ligne +"' ";
            ps = conn.prepareStatement(requet);
            res = ps.executeQuery();
            if (res.next()){
                String t1 = res.getString("titre");
                champTitre.setText(t1);
                String t2 = res.getString("genre");
                champGenre.setText(t2);
                String t3 = res.getString("duree");
                champDuree.setText(t3);
                String t4 = res.getString("date");
                champDate.setText(t4);
                String t5 = res.getString("nbplace");
                champPlace.setText(t5);
                String t6 = res.getString("prix");
                champPrix.setText(t6);
                String t7 = res.getString("heuredeseance");
                champHeure.setText(t7);
                byte[] image = res.getBytes("image");
                ImageIcon format = new ImageIcon(image);
                photo.setIcon(format);
            }
        }catch (Exception e5){
            System.out.println("--> Exception : " + e5);
        }
    }
    public static void main(String[] args) {
        MenuPrincipalVue2 menuPrincipalVue2 = new MenuPrincipalVue2();

    }
}