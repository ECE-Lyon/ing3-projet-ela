package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreationDeCompteVue extends JFrame {

    private String motDePasse;
    private String nom;
    private String prenom;
    private String identifiant;
    private JTextField champsIdentifiant;
    private JTextField champsPrenom;
    private  JTextField champsNom;
    private JPasswordField champsPasseword;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public CreationDeCompteVue() throws HeadlessException {

        super("Creation de compte");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800,800);
        this.setLocationRelativeTo(null);
        JPanel panelCreationCompte = (JPanel) this.getContentPane();
        setLayout(new GridLayout(   6 , 1));
        conn = com.company.ConnexionBD.Connexion();

        panelCreationCompte.add(titrePage());
        panelCreationCompte.add(nom());
        panelCreationCompte.add(prenom());
        panelCreationCompte.add(panel3());
        panelCreationCompte.add(panel2());
        panelCreationCompte.add(panel5());
        
    }

    public JPanel titrePage()
    {
        JPanel un2 = new JPanel(new FlowLayout());
        JLabel creation2 = new JLabel("Creation de compte");
        un2.add(creation2);
        return  un2;
    }

    public JPanel nom()
    {
        JPanel panelNom = new JPanel(new FlowLayout(FlowLayout.CENTER , 100 , 20));
        JLabel nom = new JLabel("Nom");
        panelNom.add(nom);
        champsNom = new JTextField(20);
        panelNom.add(champsNom);
        return  panelNom ;

    }

    public JPanel panel2()
    {
        JPanel deux = new JPanel(new FlowLayout(FlowLayout.CENTER , 100 , 20));
        JLabel passeword = new JLabel("Passeword");
        deux.add(passeword);
        champsPasseword = new JPasswordField(20);
        deux.add(champsPasseword);
        return  deux ;

    }

    public JPanel prenom()
    {
        JPanel panelPrenom = new JPanel(new FlowLayout(FlowLayout.CENTER , 100 , 20));
        JLabel prenom = new JLabel("Prenom");
        panelPrenom.add(prenom);
        champsPrenom = new JTextField(20);
        champsPrenom.getText();
        panelPrenom.add(champsPrenom);
        return  panelPrenom ;

    }

    public JPanel panel3()
    {
        JPanel trois = new JPanel(new FlowLayout(FlowLayout.CENTER , 100 , 20));
        JLabel identifiant = new JLabel("Identifiant");
        trois.add(identifiant);
        champsIdentifiant = new JTextField(20);
        trois.add(champsIdentifiant);
        return  trois ;

    }

    public JPanel panel5()
    {
        JPanel quatre = new JPanel(new FlowLayout(FlowLayout.CENTER , 100 , 20));
        JButton valider = new JButton("valider");
        quatre.add(valider);
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prenom = champsPrenom.getText();
                nom = champsNom.getText();
                motDePasse = champsPasseword.getText();
                identifiant = champsIdentifiant.getText();
                try{
                    String requete2 = "select * from Client where identifiant = ?";
                    ps =conn.prepareStatement(requete2);
                    ps.setString(1,identifiant);
                    rs = ps.executeQuery();
                    if (rs.next()){
                        JOptionPane.showMessageDialog(null, "Cette identifiant est déjà utilisé veuillez en choisir un autre");
                    }
                    else{
                        try{
                            String requete1 = "insert into client(identifiant,nom,prenom,motDePasse) values (?,?,?,?)";
                            ps =conn.prepareStatement(requete1);
                            ps.setString(1,identifiant);
                            ps.setString(2,nom);
                            ps.setString(3,prenom);
                            ps.setString(4,motDePasse);
                            ps.executeUpdate();
                            JOptionPane.showMessageDialog(null,"le compte à bien été crée");
                        }catch (Exception e2){
                            System.out.println("--> Exception : " + e2);
                        }finally{
                            try{
                                ps.close();
                                rs.close();
                            }catch (Exception e3){
                                System.out.println("--> Exception : " + e3);
                            }
                        }
                    }}catch (Exception e56){
                    System.out.println("--> Exception : " + e56);
                }

                champsIdentifiant.setText("");
                champsNom.setText("");
                champsPasseword.setText("");
                champsPrenom.setText("");

            }

        });

        return  quatre ;

    }

    public static void main(String[] args) {


        CreationDeCompteVue creationDeComptevue = new CreationDeCompteVue();
        creationDeComptevue.setVisible(true);


    }

}

