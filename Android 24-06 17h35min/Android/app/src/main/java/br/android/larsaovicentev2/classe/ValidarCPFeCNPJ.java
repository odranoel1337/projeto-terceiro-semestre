package br.android.larsaovicentev2.classe;

import br.android.larsaovicentev2.activitys.CadastroPessoaFisicaActivity;

public class ValidarCPFeCNPJ {

    public boolean validadorCPF(String cpf) {

        CadastroPessoaFisicaActivity pFisica = new CadastroPessoaFisicaActivity();

        if(!cpf.matches("[0-9]+")){
            return false;
        }

        if(cpf.isEmpty()){
            return false;
        }
        if(cpf.length() > 11){
            return false;
        }

        if(cpf.length() < 11){
            return false;
        }
        int somador = 0, digito1, digito2;

        char[] arrayValores = cpf.toCharArray();
        int[] valorInteiros = new int[arrayValores.length];
        int[] valorInteiros2 = new int[arrayValores.length];

        for (int i = 0; i < arrayValores.length; i++) {
            valorInteiros[i] = Integer.parseInt(String.valueOf(arrayValores[i]));
        }

        for (int i = 0; i < arrayValores.length; i++) {
            valorInteiros2[i] = Integer.parseInt(String.valueOf(arrayValores[i]));
        }


        for (int i = 0; i < 9; i++) {
            somador += valorInteiros[i] * (i + 1);
        }

        digito1 = somador % 11;

        if (digito1 == 10) {
            digito1 = 0;
        }
        somador = 0;
        for (int i = 0; i < 10; i++) {
            somador += valorInteiros[i] * i;
        }
        digito2 = somador % 11;

        if (digito2 == 10) {
            digito2 = 0;
        }

        valorInteiros[9] = digito1;
        valorInteiros[10] = digito2;

        if(valorInteiros[9] == valorInteiros2[9] && valorInteiros[10] == valorInteiros2[10]){
            return true;
        }else{
            return false;
        }
    }

    public boolean validadorCNPJ(String cnpj){

        if(!cnpj.matches("[0-9]+")){
            return false;
        }

        if(cnpj.isEmpty()){
            return false;
        }

        if(cnpj.length() != 14 ){
            return false;
        }

        int somador = 0, digito1, digito2;

        char[] arrayValores = cnpj.toCharArray();
        int[] valorInteiros = new int[arrayValores.length];
        int[] valorInteiros2 = new int[arrayValores.length];

        for (int i = 0; i < arrayValores.length; i++) {
            valorInteiros[i] = Integer.parseInt(String.valueOf(arrayValores[i]));
        }

        for (int i = 0; i < arrayValores.length; i++) {
            valorInteiros2[i] = Integer.parseInt(String.valueOf(arrayValores[i]));
        }
        int j = 0;
        for (int i = 5; i > 1; i--) {
            int a = i;
            int b = j;
            somador = (somador + valorInteiros[j] * i);
            j++;
            int c = j;
        }
        for(int i = 9; i > 1; i--){
            int a = i;
            int b = j;
            somador = (somador + valorInteiros[j] * i);
            j++;
            int c = j;
        }

        digito1 = somador % 11;

        if(digito1 < 2){
            digito1 = 0;
        }else{
            digito1 = 11 - digito1;
        }

        somador = 0;
        j = 0;
        for (int i = 6; i > 1; i--) {
            int a = i;
            int b = j;
            somador = (somador + valorInteiros[j] * i);
            j++;
            int c = j;
        }
        for(int i = 9; i > 1; i--){
            int a = i;
            int b = j;
            somador = (somador + valorInteiros[j] * i);
            j++;
            int c = j;
        }

        digito2 = somador % 11;

        if(digito2 < 2){
            digito2 = 0;
        }else{
            digito2 = 11 - digito2;
        }

        valorInteiros[12] = digito1;
        valorInteiros[13] = digito2;

        if(valorInteiros[12] == valorInteiros2[12] && valorInteiros[13] == valorInteiros2[13]){
            return true;
        }else{
            return false;
        }
    }
}
