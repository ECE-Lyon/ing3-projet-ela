package com.company;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AjoutFilmVue {


    private String genre;
    private String titre;
    private String duree;
    private String place;
    private String prix;
    private String date;
    private String heure;
    private JTextField champGenre;
    private JTextField champsHeure;
    private JTextField champPrix;
    private JTextField champPlace;
    private JTextField champTitre;
    private JTextField champDuree;
    private JTextField champDate;
    private Connection conn = null;
    private final ResultSet rs = null;
    private PreparedStatement ps = null;
    private ImageIcon format = null;
    byte[]photo = null;





    public AjoutFilmVue() throws HeadlessException {
        conn = com.company.ConnexionBD.Connexion();
        JFrame jFrame;
        jFrame = new JFrame("Menu principal administrateur");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(800, 800);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        JPanel panelMenuPrincipalAdministrateur = (JPanel) jFrame.getContentPane();
        jFrame.setLayout(new GridLayout(10, 1));
        jFrame.add(titrePage());
        panelMenuPrincipalAdministrateur.add(titreFilm());
        panelMenuPrincipalAdministrateur.add(genreFilm());
        panelMenuPrincipalAdministrateur.add(dureeFilm());
        panelMenuPrincipalAdministrateur.add(dateFilm());
        panelMenuPrincipalAdministrateur.add(placeFilm());
        panelMenuPrincipalAdministrateur.add(prixFilm());
        panelMenuPrincipalAdministrateur.add( heureFilm());
        panelMenuPrincipalAdministrateur.add(boutonPhoto());
        panelMenuPrincipalAdministrateur.add(panelValidation());


    }

    public JPanel titrePage() {
        JPanel panelTitre = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JLabel titre = new JLabel("Page de saisie des films");
        panelTitre.add(titre);
        return panelTitre;
    }

    public JPanel titreFilm()
    {
        JPanel panelTitreFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel titre = new JLabel("Titre:");
        panelTitreFilm.add(titre);
        champTitre = new JTextField(20);
        panelTitreFilm.add(champTitre);
        return  panelTitreFilm ;

    }
    public JPanel heureFilm()
    {
        JPanel panelHeureFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel heure = new JLabel("Heure de la séance :");
        panelHeureFilm.add(heure);
        champsHeure = new JTextField(20);
        panelHeureFilm.add(champsHeure);
        return  panelHeureFilm ;

    }

    public JPanel genreFilm()
    {
        JPanel panelGenreFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel genre = new JLabel("Genre:");
        panelGenreFilm.add(genre);
        champGenre = new JTextField(20);
        panelGenreFilm.add(champGenre);
        return  panelGenreFilm ;

    }
    public JPanel dureeFilm()
    {
        JPanel panelDureeFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel duree = new JLabel("Durée:");
        panelDureeFilm.add(duree);
        champDuree = new JTextField(20);
        panelDureeFilm.add(champDuree);
        return  panelDureeFilm ;

    }
    public JPanel dateFilm()
    {
        JPanel panelDateFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel date = new JLabel("Date:");
        panelDateFilm.add(date);
        champDate = new JTextField(20);
        panelDateFilm.add(champDate);
        return  panelDateFilm ;

    }
    public JPanel placeFilm()
    {
        JPanel panelPlaceFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel place = new JLabel("Place:");
        panelPlaceFilm.add(place);
        champPlace= new JTextField(20);
        panelPlaceFilm.add(champPlace);
        return  panelPlaceFilm ;

    }
    public JPanel prixFilm()
    {
        JPanel panelPlaceFilm = new JPanel(new FlowLayout(FlowLayout.CENTER , 50 , 5));
        JLabel prix = new JLabel("Prix:");
        panelPlaceFilm.add(prix);
        champPrix= new JTextField(20);
        panelPlaceFilm.add(champPrix);
        return  panelPlaceFilm ;

    }


    public JPanel panelValidation() {

        JPanel Validation = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton boutonValidation = new JButton("Validation");
        Validation.add(boutonValidation);
        boutonValidation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genre = champGenre.getText();
                titre = champTitre.getText();
                date = champDate.getText();
                duree = champDuree.getText();
                prix = champPrix.getText();
                place = champPlace.getText();
                heure = champsHeure.getText();
                try{

                    ps =conn.prepareStatement("insert into films(genre,titre,duree,date,nbplace,prix,heuredeseance,image) values (?,?,?,?,?,?,?,?)");
                    ps.setString(1,genre);
                    ps.setString(2,titre);
                    ps.setString(3,duree);
                    ps.setString(4,date);
                    ps.setString(5,place);
                    ps.setString(6,prix);
                    ps.setString(7,heure);
                    ps.setBytes(8,photo);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"le film à bien été crée");
                }catch (Exception e2){
                    System.out.println("--> Exception : " + e2);
                }finally{
                    try{
                        ps.close();

                    }catch (Exception e3){
                        System.out.println("--> Exception : " + e3);
                    }
                }

                champTitre.setText("");
                champDate.setText("");
                champDuree.setText("");
                champGenre.setText("");
                champPlace.setText("");
                champPrix.setText("");

            }
        });

        return Validation;
    }



    public JPanel boutonPhoto() {
        JPanel panelBoutonPhoto = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton choixPhoto = new JButton("Choisir une photo");
        JTextField champsPath = new JTextField(20);
        panelBoutonPhoto.add(choixPhoto);
        panelBoutonPhoto.add(champsPath);
        choixPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                com.company.ConnexionBD v = new com.company.ConnexionBD();
                v.filen();
                String vpath = v.getp();
                if (vpath==null){

                }else {
                    champsPath.setText(vpath);

                    try {
                        File image = new File(vpath);
                        FileInputStream fs = new FileInputStream(image);
                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        byte[] b = new byte[1024];
                        for (int re; (re = fs.read(b)) != -1; ) {
                            bs.write(b, 0, re);

                        }
                        photo = bs.toByteArray();


                    } catch (Exception e10) {
                        System.out.println("--> Exception : " + e10);
                    }
                }
                champsPath.setText("");
            }
        });


        return panelBoutonPhoto;
    }



    public static void main(String[] args) {

        com.company.AjoutFilmVue ajoutFilmVue = new com.company.AjoutFilmVue();

    }}





