package com.example.myapplication2;

public class utilisateur {
    public String nom,prenom,mail,motsdepasse,etat;
    public int agen,gamil,camera,contact,horloge,telephone,messages,map,photo,notif;
    public int getAgen() {
        return agen;
    }

    public void setAgen(int agen) {
        this.agen = agen;
    }

    public int getGamil() {
        return gamil;
    }

    public void setGamil(int gamil) {
        this.gamil = gamil;
    }

    public int getCamera() {
        return camera;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public int getHorloge() {
        return horloge;
    }

    public void setHorloge(int horloge) {
        this.horloge = horloge;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getNotif() {
        return notif;
    }

    public void setNotif(int notif) {
        this.notif = notif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotsdepasse() {
        return motsdepasse;
    }

    public void setMotsdepasse(String motsdepasse) {
        this.motsdepasse = motsdepasse;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public utilisateur(){

   }
    public utilisateur(String nom, String prenom, String mail,String motsdepasse,String etat ,int agen,int gamail, int camera,int contact,int horloge,int telephone ,int messages,int map,int photo,int notif) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motsdepasse=motsdepasse;
        this.etat=etat;
        this.agen=agen;
        this.gamil=gamail;
        this.camera=camera;
        this.contact=contact;
        this.horloge=horloge;
        this.telephone=telephone;
        this.messages=messages;
        this.map=map;
        this.photo=photo;
        this.notif=notif;
    }
}
