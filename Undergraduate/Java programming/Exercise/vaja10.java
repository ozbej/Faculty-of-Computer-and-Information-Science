interface Kodirnik{
    String enkripcija(String besedilo);
    String dekripcija(String besedilo);            
}

class CezarjevAlgoritem implements Kodirnik{
    int zamik;
    
    CezarjevAlgoritem(int zamik){
        this.zamik = zamik;
    }
    
    @Override
    public String enkripcija(String besedilo){
        String abeceda="abcdefghijklmnopqrstuvwxyz";
        String velikaAbeceda="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer kodiranoBesedilo = new StringBuffer("");
        int ia;
        int novZamik;
        for (int i = 0; i < besedilo.length(); i++) {
            char c = besedilo.charAt(i);
            if(Character.isLetter(c) && Character.isLowerCase(c)){
                ia = abeceda.indexOf(c);
                if(ia + zamik >= abeceda.length()){
                    novZamik = ia + zamik - abeceda.length();
                    while(novZamik < 0 || novZamik >= abeceda.length())
                        novZamik -= abeceda.length();
                    kodiranoBesedilo.append(abeceda.charAt(novZamik));
                }

                else if(ia + zamik < 0){
                    novZamik = ia + zamik + abeceda.length();
                    while(novZamik < 0)
                        novZamik += abeceda.length();
                    kodiranoBesedilo.append(abeceda.charAt(novZamik));
                }
                else
                    kodiranoBesedilo.append(abeceda.charAt(ia + zamik));
            }
            else if(Character.isLetter(c) && Character.isUpperCase(c)){
                ia = velikaAbeceda.indexOf(c);
                if(ia + zamik >= velikaAbeceda.length()){
                    novZamik = ia + zamik - velikaAbeceda.length();
                    while(novZamik < 0 || novZamik >= velikaAbeceda.length())
                        novZamik -= velikaAbeceda.length();
                    kodiranoBesedilo.append(velikaAbeceda.charAt(novZamik));
                }

                else if(ia + zamik < 0){
                    novZamik = ia + zamik + velikaAbeceda.length();
                    while(novZamik < 0)
                        novZamik += velikaAbeceda.length();
                    kodiranoBesedilo.append(velikaAbeceda.charAt(novZamik));
                }
                else
                    kodiranoBesedilo.append(velikaAbeceda.charAt(ia + zamik));
            }
        }
        return kodiranoBesedilo.toString();
    }
    
    @Override
    public String dekripcija(String besedilo){
        String abeceda="abcdefghijklmnopqrstuvwxyz";
        String velikaAbeceda="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer dekodiranoBesedilo = new StringBuffer("");
        int ia;
        int novZamik;
        for (int i = 0; i < besedilo.length(); i++) {
            char c = besedilo.charAt(i);
            if(Character.isLetter(c) && Character.isLowerCase(c)){
                ia = abeceda.indexOf(c);
                if(ia + zamik >= abeceda.length()){
                    novZamik = ia + zamik - abeceda.length();
                    while(novZamik < 0 || novZamik >= abeceda.length())
                        novZamik -= abeceda.length();
                    dekodiranoBesedilo.append(abeceda.charAt(novZamik));
                }

                else if(ia + zamik < 0){
                    novZamik = ia + zamik + abeceda.length();
                    while(novZamik < 0)
                        novZamik += abeceda.length();
                    dekodiranoBesedilo.append(abeceda.charAt(novZamik));
                }
                else
                    dekodiranoBesedilo.append(abeceda.charAt(ia - zamik));
            }
            else if(Character.isLetter(c) && Character.isUpperCase(c)){
                ia = velikaAbeceda.indexOf(c);
                if(ia + zamik >= velikaAbeceda.length()){
                    novZamik = ia + zamik - velikaAbeceda.length();
                    while(novZamik < 0 || novZamik >= velikaAbeceda.length())
                        novZamik -= velikaAbeceda.length();
                    dekodiranoBesedilo.append(velikaAbeceda.charAt(novZamik));
                }

                else if(ia + zamik < 0){
                    novZamik = ia + zamik + velikaAbeceda.length();
                    while(novZamik < 0)
                        novZamik += velikaAbeceda.length();
                    dekodiranoBesedilo.append(velikaAbeceda.charAt(novZamik));
                }
                else
                    dekodiranoBesedilo.append(velikaAbeceda.charAt(ia - zamik));
            }
        }
        return dekodiranoBesedilo.toString();
    }
}

public class vaja10{
    public static void main(String[] args) {
        CezarjevAlgoritem ca = new CezarjevAlgoritem(-150);
        System.out.println(ca.enkripcija("abcABCabss"));
    }
}