package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class LoginVue extends JFrame {
    private JPasswordField champsPasseword;
    private JTextField champsIdentifiant;
    private String identifiant;
    private String motDePasse;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;



    public LoginVue() throws HeadlessException {
        super("login");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        JPanel menu = (JPanel) this.getContentPane();
        setLayout(new GridLayout(4, 1));
        menu.add(panel1());
        menu.add(panel3());
        menu.add(panel2());
        menu.add(panel4());
        conn = com.company.ConnexionBD.Connexion();

    }

    public JPanel panel1()
    {
        JPanel un = new JPanel(new FlowLayout());
        JLabel creation = new JLabel("LOGIN");
        un.add(creation);
        return  un;
    }

    public JPanel panel2()
    {
        JPanel deux = new JPanel(new FlowLayout(FlowLayout.CENTER , 100 , 20));
        JLabel passeword = new JLabel("passeword");
        deux.add(passeword);
        champsPasseword = new JPasswordField(20);
        deux.add(champsPasseword);
        return  deux ;

    }

    public JPanel panel3()
    {
        JPanel trois = new JPanel(new FlowLayout(FlowLayout.CENTER , 100 , 20));
        JLabel identifiant = new JLabel("identifiant");
        trois.add(identifiant);
        champsIdentifiant = new JTextField(20);
        trois.add(champsIdentifiant);
        return  trois ;

    }

    public JPanel panel4()
    {


        JPanel quatre = new JPanel(new FlowLayout(FlowLayout.CENTER , 100 , 20));
        JButton valider = new JButton("valider");
        quatre.add(valider);

        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identifiant = champsIdentifiant.getText();
                motDePasse = champsPasseword.getText();
                String requete = "select * from Client where identifiant = ? and motDePasse = ?  ";
                try{
                    ps = conn.prepareStatement(requete);
                    ps.setString(1,identifiant);
                    ps.setString(2,motDePasse);
                    rs = ps.executeQuery();
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null, "connexion reussi");
                        com.company.MenuPrincipalVue2 menuPrincipal = new com.company.MenuPrincipalVue2();


                    }
                    else if (identifiant.equals("administrateur") && motDePasse.equals("motdepasseadministrateur")){
                        JOptionPane.showMessageDialog(null, "Connexion reussie !");
                        com.company.MenuPrincipalAdministrateurVue menuPrincipalAdministrateur = new com.company.MenuPrincipalAdministrateurVue();

                    }
                    else{JOptionPane.showMessageDialog(null,"mot de passe ou identifiant incorrect veuillez ressayer");}
                }catch (Exception e1){
                    System.out.println("--> Exception : " + e1);
                }


                champsIdentifiant.setText("");
                champsPasseword.setText("");
            }
        });

        JButton creeUnCompte= new JButton("cree un compte");
        creeUnCompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                com.company.CreationDeCompteVue creationDeComptevue = new com.company.CreationDeCompteVue();




            }
        });
        quatre.add(creeUnCompte);

        return  quatre ;

    }



    public static void main(String[] args) {

        LoginVue login = new LoginVue();

    }


}
