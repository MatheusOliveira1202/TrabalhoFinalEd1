package br.unirio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OrdenacaoTopologica
{
	private class Elo
	{
		/* Identificação do elemento. */
		public int chave;
		
		/* Número de predecessores. */
		public int contador;
		
		/* Aponta para o próximo elo da lista. */
		public Elo prox;
		
		/* Aponta para o primeiro elemento da lista de sucessores. */
		public EloSuc listaSuc;
		
		public Elo()
		{
			prox = null;
			contador = 0;
			listaSuc = null;
		}
		
		public Elo(int chave, int contador, Elo prox, EloSuc listaSuc)
		{
			this.chave = chave;
			this.contador = contador;
			this.prox = prox;
			this.listaSuc = listaSuc;
		}
	}
	
	private class EloSuc
	{
		/* Aponta para o elo que é sucessor. */
		public Elo id;
		
		/* Aponta para o próximo elemento. */
		public EloSuc prox;
		
		public EloSuc()
		{
			id = null;
			prox = null;
		}
		
		public EloSuc(Elo id, EloSuc prox)
		{
			this.id = id;
			this.prox = prox;
		}
	}


	/* Ponteiro (referência) para primeiro elemento da lista. */
	private Elo prim;
	
	/* Número de elementos na lista. */
	private int n;
		
	public OrdenacaoTopologica()
	{
		prim = null;
		n = 0;
	}
	
	/* Método responsável pela leitura do arquivo de entrada. */
	public void realizaLeitura(String nomeEntrada)
	{
		/* Preencher. */
		int x, y;
		
		File file = new File(nomeEntrada);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String textoDoDocumento = null, linha;
			while((linha = br.readLine()) != null){
				textoDoDocumento += "\n" + linha;
				linha = linha.replaceAll(" ", "");
				//System.out.println(aux.indexOf("<") -1);
				x = Integer.parseInt(linha.substring(0, linha.indexOf("<")));
				y = Integer.parseInt(linha.substring(linha.indexOf("<") + 1));
				//System.out.println("testing");
				//imprimeLista();
				
				verificaPar(x,y);
				
				
				//imprimeLista();
				//System.out.println(x + "||" + y);
			}
			//imprimeLista();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void verificaPar(int x, int y) {
		
		if(!existeNaLista(x))
		{
			insereNaLista(x);
			//imprimeLista();
		}
		if(!existeNaLista(y))
		{
			insereNaLista(y);
			//imprimeLista();
		}
	}
	
	public void imprimeLista() 
	{
		Elo p;
		for(p = prim; p != null; p = p.prox)
		{
			System.out.print(p.chave + ";");
		}
	}
	
	private void insereNaLista(int numeroAInserir) 
	{
		if(prim != null) 
		{
			Elo p;
			for(p = prim; p != null; p = p.prox)
			{
				if(p.prox == null)
				{
					p.prox = new Elo(numeroAInserir, n, null, null);
					n += 1;
					break;
				}
			}
		}
		else
		{
			prim = new Elo(numeroAInserir, n, null, null);
			n += 1;
		}
	}
	
	private boolean existeNaLista(int numeroASerVerificado) 
	{
		Elo p;
		for(p = prim; p != null; p = p.prox) 
		{
			if(p.chave == numeroASerVerificado)
			{
				return true;
			}
		}
		return false;
	}
	
	/* Método para impressão do estado atual da estrutura de dados. */
	private void debug()
	{
		/* Preencher. */
	}
	
	/* Método responsável por executar o algoritmo. */
	public boolean executa()
	{
		/* Preencher. */
		
		return false;
	}
}