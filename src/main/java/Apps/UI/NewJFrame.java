/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apps.UI;

import Info.Allergy;
import Info.Displayable;
import Info.Drug;
import Info.Patient;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class NewJFrame extends javax.swing.JFrame {

    private List<Patient> patientsList;
    private Patient patient;
    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        patientsList = new ArrayList<>();
        initComponents();
        massBtnEnalbed(false);
        massBtnREnalbed(false);
        jbnPatientRemove.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        patientPanel = new javax.swing.JPanel();
        jspPatient = new javax.swing.JScrollPane();
        jlPatient = new javax.swing.JList<>();
        lblPatient = new javax.swing.JLabel();
        jbnPatient = new javax.swing.JButton();
        jbnPatientRemove = new javax.swing.JButton();
        DrugPanel = new javax.swing.JPanel();
        jspDrug = new javax.swing.JScrollPane();
        jlDrug = new javax.swing.JList<>();
        lblDrug = new javax.swing.JLabel();
        jbnDrug = new javax.swing.JButton();
        jbnDrugRemove = new javax.swing.JButton();
        AllergyPanel = new javax.swing.JPanel();
        jspAllergy = new javax.swing.JScrollPane();
        jlAllergy = new javax.swing.JList<>();
        lblAllergy = new javax.swing.JLabel();
        jbnAllergy = new javax.swing.JButton();
        jbnAllergyRemove = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 800));
        setMinimumSize(new java.awt.Dimension(800, 800));

        jlPatient.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlPatient.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlPatientValueChanged(evt);
            }
        });
        jspPatient.setViewportView(jlPatient);

        lblPatient.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPatient.setText("Patients");

        jbnPatient.setText("Add Patient");
        jbnPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnPatientActionPerformed(evt);
            }
        });

        jbnPatientRemove.setText("Remove Patient");
        jbnPatientRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnPatientRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout patientPanelLayout = new javax.swing.GroupLayout(patientPanel);
        patientPanel.setLayout(patientPanelLayout);
        patientPanelLayout.setHorizontalGroup(
            patientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(patientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(patientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspPatient)
                    .addComponent(lblPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(patientPanelLayout.createSequentialGroup()
                        .addComponent(jbnPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbnPatientRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        patientPanelLayout.setVerticalGroup(
            patientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, patientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPatient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspPatient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(patientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnPatient)
                    .addComponent(jbnPatientRemove))
                .addContainerGap())
        );

        jlDrug.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlDrugValueChanged(evt);
            }
        });
        jspDrug.setViewportView(jlDrug);

        lblDrug.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDrug.setText("Drugs");

        jbnDrug.setText("Add Drugs");
        jbnDrug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnDrugActionPerformed(evt);
            }
        });

        jbnDrugRemove.setText("Remove Drugs");
        jbnDrugRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnDrugRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DrugPanelLayout = new javax.swing.GroupLayout(DrugPanel);
        DrugPanel.setLayout(DrugPanelLayout);
        DrugPanelLayout.setHorizontalGroup(
            DrugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrugPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DrugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDrug, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jspDrug)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrugPanelLayout.createSequentialGroup()
                        .addComponent(jbnDrug, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbnDrugRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        DrugPanelLayout.setVerticalGroup(
            DrugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrugPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDrug, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspDrug, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrugPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnDrug)
                    .addComponent(jbnDrugRemove))
                .addContainerGap())
        );

        jlAllergy.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlAllergyValueChanged(evt);
            }
        });
        jspAllergy.setViewportView(jlAllergy);

        lblAllergy.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblAllergy.setText("Allergys");

        jbnAllergy.setText("Add Allergies");
        jbnAllergy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAllergyActionPerformed(evt);
            }
        });

        jbnAllergyRemove.setText("Remove Allergies");
        jbnAllergyRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnAllergyRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AllergyPanelLayout = new javax.swing.GroupLayout(AllergyPanel);
        AllergyPanel.setLayout(AllergyPanelLayout);
        AllergyPanelLayout.setHorizontalGroup(
            AllergyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AllergyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AllergyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAllergy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jspAllergy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AllergyPanelLayout.createSequentialGroup()
                        .addComponent(jbnAllergy, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbnAllergyRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        AllergyPanelLayout.setVerticalGroup(
            AllergyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AllergyPanelLayout.createSequentialGroup()
                .addComponent(lblAllergy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspAllergy, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AllergyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnAllergy)
                    .addComponent(jbnAllergyRemove))
                .addContainerGap())
        );

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(patientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AllergyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DrugPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(patientPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addComponent(DrugPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AllergyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void massBtnEnalbed(boolean b){
        jbnAllergy.setEnabled(b);
        jbnDrug.setEnabled(b);
    }
    private void massBtnREnalbed(boolean b){
        jbnAllergyRemove.setEnabled(b);
        jbnDrugRemove.setEnabled(b);
    }
    private void jlPatientValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlPatientValueChanged
        if(jlPatient.getSelectedIndex() == -1){
            patient = null;
            return;
        }
        patient = patientsList.get(jlPatient.getSelectedIndex());
        updateSelectedPatientItems(patient);
        massBtnEnalbed(true);
    }//GEN-LAST:event_jlPatientValueChanged

    private void jbnPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnPatientActionPerformed
        UI.NewNewJDialog dialog = new UI.NewNewJDialog(this,true);
        dialog.setVisible(true);
        if (dialog.hasNewPatient()){
            patientsList.add(dialog.getPatient());
            updatePatientListItem();
            jbnPatientRemove.setEnabled(true);
            jlPatient.setSelectedIndex(0);
        }



    }//GEN-LAST:event_jbnPatientActionPerformed

    private void updatePatientListItem(){
        jlPatient.setListData(patientsList.stream().map(Displayable::getDisplayName).toArray(String[]::new));

    }

    private void updateSelectedPatientItems(Patient patient){
        if(patient==null){
         jlDrug.setListData(new String[]{});
         jlAllergy.setListData(new String[]{});

        }else {
            jlDrug.setListData(patient.getDrugsPrescribed().stream().map(Drug::getDisplayName).toArray(String[]::new));
            jlAllergy.setListData(patient.getPatientAllergies().stream().map(Allergy::getName).toArray(String[]::new));
        }
    }

    private void jbnDrugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnDrugActionPerformed
        JDialog dialog = new UI.newJDialog(this, true, patient, UI.newJDialog.DRUGS);
        dialog.setVisible(true);
        updateSelectedPatientItems(patient);
    }//GEN-LAST:event_jbnDrugActionPerformed

    private void jbnAllergyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAllergyActionPerformed
        JDialog dialog = new UI.newJDialog(this, true, patient, UI.newJDialog.ALLERGIES);
        dialog.setVisible(true);
        updateSelectedPatientItems(patient);
    }//GEN-LAST:event_jbnAllergyActionPerformed

    private void jbnDrugRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnDrugRemoveActionPerformed
        if(jlDrug.getSelectedIndex() == -1){
            return;
        }
        int[] arr =jlDrug.getSelectedIndices();
        for(int i = arr.length-1; i>-1; i--){
            patient.removeDrug(arr[i]);
        }
        updateSelectedPatientItems(patient);

    }//GEN-LAST:event_jbnDrugRemoveActionPerformed

    private void jbnPatientRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnPatientRemoveActionPerformed
        if(jlPatient.getSelectedIndex() == -1){
            return;
        }

        patientsList.remove(jlPatient.getSelectedIndex());


        updatePatientListItem();
        updateSelectedPatientItems(patient);
        if (patientsList.isEmpty()){
            jbnPatientRemove.setEnabled(false);
        }
        massBtnEnalbed(false);
    }//GEN-LAST:event_jbnPatientRemoveActionPerformed

    private void jbnAllergyRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnAllergyRemoveActionPerformed
        if(jlAllergy.getSelectedIndex() == -1){
            return;
        }
        int[] arr =jlAllergy.getSelectedIndices();
        for(int i = arr.length-1; i>-1; i--){
            patient.removeAllergy(arr[i]);
        }
        updateSelectedPatientItems(patient);
    }//GEN-LAST:event_jbnAllergyRemoveActionPerformed

    private void jlDrugValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlDrugValueChanged
        if(jlDrug.getSelectedIndex() == -1){
            massBtnREnalbed(false);
        }else {massBtnREnalbed(true);}
    }//GEN-LAST:event_jlDrugValueChanged

    private void jlAllergyValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlAllergyValueChanged
        if(jlAllergy.getSelectedIndex() == -1){
            massBtnREnalbed(false);
        }else {massBtnREnalbed(true);}
    }//GEN-LAST:event_jlAllergyValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AllergyPanel;
    private javax.swing.JPanel DrugPanel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton jbnAllergy;
    private javax.swing.JButton jbnAllergyRemove;
    private javax.swing.JButton jbnDrug;
    private javax.swing.JButton jbnDrugRemove;
    private javax.swing.JButton jbnPatient;
    private javax.swing.JButton jbnPatientRemove;
    private javax.swing.JList<String> jlAllergy;
    private javax.swing.JList<String> jlDrug;
    private javax.swing.JList<String> jlPatient;
    private javax.swing.JScrollPane jspAllergy;
    private javax.swing.JScrollPane jspDrug;
    private javax.swing.JScrollPane jspPatient;
    private javax.swing.JLabel lblAllergy;
    private javax.swing.JLabel lblDrug;
    private javax.swing.JLabel lblPatient;
    private javax.swing.JPanel patientPanel;
    // End of variables declaration//GEN-END:variables
}
