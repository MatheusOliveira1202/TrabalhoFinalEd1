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
		/* Identifica��o do elemento. */
		public int chave;
		
		/* N�mero de predecessores. */
		public int contador;
		
		/* Aponta para o pr�ximo elo da lista. */
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
		/* Aponta para o elo que � sucessor. */
		public Elo id;
		
		/* Aponta para o pr�ximo elemento. */
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


	/* Ponteiro (refer�ncia) para primeiro elemento da lista. */
	private Elo prim;
	
	/* N�mero de elementos na lista. */
	private int n;
		
	public OrdenacaoTopologica()
	{
		prim = null;
		n = 0;
	}
	
	/* M�todo respons�vel pela leitura do arquivo de entrada. */
	public void realizaLeitura(String nomeEntrada)
	{
		//cria as int's que iram receber os numeros que est�o no documento
		int x, y;
		
		//l�gica de leitura de arquivo
		File file = new File(nomeEntrada);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String textoDoDocumento = null, linha;
			//a cada linha lida ele executa o c�digo abaixo
			while((linha = br.readLine()) != null){
				textoDoDocumento += "\n" + linha;
				//limpa os espa�os vazios da linha, por exemplo de "1 < 2" pra "1<2"
				linha = linha.replaceAll(" ", "");
				//pega os valores de 'x' e 'y' no arquivo de texto e converte para int
				x = Integer.parseInt(linha.substring(0, linha.indexOf("<")));
				y = Integer.parseInt(linha.substring(linha.indexOf("<") + 1));
				//faz a verifica��o dos elementos do par para ver se existem na lista e adicionarem na lista se ainda n�o existirem
				verificaPar(x,y);
			}
			//imprimeLista();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		executa();
	}
	
	//faz a verifica��o do par
	private void verificaPar(int x, int y) 
	{	
		//verifica se existe 'x' na lista e se n�o existe ele chama o m�todo de adicionar que adiciona no final da lista
		if(!existeNaLista(x))
		{
			insereNaLista(x);
		}
		//verifica se existe 'y' na lista e se n�o existe ele chama o m�todo de adicionar que adiciona no final da lista
		if(!existeNaLista(y))
		{
			insereNaLista(y);
		}
		
		//cria a referencia dos elos 'x' e 'y'
		Elo eloX;
		Elo eloY;
		
		//inicializa os elos fazendo uma busca por chave com as chaves que foram passadas no m�todos
		eloX = buscaEloPorChave(x);
		eloY = buscaEloPorChave(y);
		
		/*se o eloX n�o tiver uma lista de sucessores ele cria uma lista de sucessores sendo o primeiro sucessor o elemento
		 *  'y' e incrementa o contador de y*/
		if(eloX.listaSuc == null)
		{
			eloX.listaSuc = new EloSuc(eloY, null);
			eloY.contador += 1;
		}
		/*se ele j� tiver uma lista de sucessores ele apenas adiciona o 'y' na sua lista de sucessores e incrementa o
		 * contador de 'y'*/
		else
		{
			EloSuc antigo = eloX.listaSuc;
			eloX.listaSuc = new EloSuc(eloY, antigo);
			eloY.contador += 1;
		}
	}
	
	//m�todo que percorre a lista e busca um elemento da lista pelo valor da chave que � dado
	private Elo buscaEloPorChave(int chaveASerBuscada) 
	{
		Elo p;
		for(p = prim; p != null; p = p.prox) 
		{
			if(p.chave == chaveASerBuscada) 
			{
				return p;
			}
		}
		return null;
	}
	
	//m�todo para inserir a lista o elemento
	private void insereNaLista(int numeroAInserir) 
	{
		/*se a lista n�o est� vazia ele vai percorrer a lista e vai adicionar o elemento no final da lista, adicionando o elemento quando
		� checado o �ltimo elemento, adicionamos um valor � 'n' e passamos um contador zerado pois est� sendo adicionado 
		agora*/
		if(prim != null) 
		{
			Elo p;
			for(p = prim; p != null; p = p.prox)
			{
				if(p.prox == null)
				{
					p.prox = new Elo(numeroAInserir, 0, null, null);
					n += 1;
					break;
				}
			}
		}
		/*caso a lista esteja vazia ele adiciona o elemento na primeira posi��o da lista e adiciona um valor � 'n' tamb�m
		 * e passamos um contador zerado pois o elo est� sendo criado agora*/
		else
		{
			prim = new Elo(numeroAInserir, 0, null, null);
			n += 1;
		}
	}
	
	/*m�todo para verificar se existe um elo na lista fazendo a busca atrav�s da chave*/
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
	
	// M�todo para impress�o do estado atual da estrutura de dados. 
	private void debug(Elo elo)
	{
		//string para guardar o valor dos sucessores que aser� adicionado no final do print line
		String sucessores = "";
		//EloSuc para verificar a lista de sucessores do elo que foi passado para o m�todo
		EloSuc p = elo.listaSuc;
		//se a lista de sucessores j� existir e n�o for nula, ele percorre toda a lista de sucessores e vai somando na string 
		if(elo.listaSuc != null)
		{
			for(p = elo.listaSuc; p != null; p = p.prox) 
			{
				sucessores += p.id.chave + " -> ";
			}
			sucessores += "NULL";
		}
		//caso a lista de sucessores seja nula, o valor da vari�vel ser� nulo para que que n�o retorne nenhum valor nessa string 
		else 
		{
			sucessores = "NULL";
		}
		//faz o print line do elo com as informa��es necess�rias e a string de sucessores que foi montada acima
		System.out.println(elo.chave + " predecessores: " + elo.contador + ", sucessores : " + sucessores);
	}
	
	/* M�todo respons�vel por executar o algoritmo. */
	public boolean executa()
	{
		//depois que j� criou a lista inteira, l� a lista e faz o debug de cada n�
		
		Elo d;
		for(d = prim; d != null; d = d.prox) 
		{
			debug(d);
		}
		
		System.out.println("");
		System.out.println("Ordena��o Topol�gica");
		
		/* Preencher. */
		Elo p = prim, q; prim = null;
		while(p != null) 
		{
			q = p;
			p = q.prox;
			if(q.contador == 0) 
			{
				q.prox = prim;
				prim = q;
			}
			/*else 
			{
				System.out.println("Conjunto n�o � parcialmente ordenado.");
				return false;
			}*/
		}
		
		for(q = prim; q != null; q = q.prox) 
		{
			System.out.print(q.chave + " ");
			n--;
			EloSuc t;
			for(t = q.listaSuc; t != null; t = t.prox)
			{
				t.id.contador--;
				if(t.id.contador == 0)
				{
					insereNalistaComZeroPredecessores(q, t.id);
					removeDaListaDeSucessores(q.listaSuc, t);
					removeDaListaComZeroPredecessores(q, q);
				}
			}
		}
		return false;
	}
	
	private void removeDaListaDeSucessores(EloSuc listaDeSucessores, EloSuc aSerRemovido) 
	{
		EloSuc w;
		for(w = listaDeSucessores; w != null; w = w.prox)
		{
			if(w == listaDeSucessores && w == aSerRemovido)
			{
				listaDeSucessores = w.prox;
				//break;
			}
			if(w.prox == aSerRemovido)
			{
				w.prox = w.prox.prox;
				break;
			}
		}
	}
	
	private void removeDaListaComZeroPredecessores(Elo lista, Elo aSerRemovido) 
	{
		Elo g;
		for(g = lista; g != null; g = g.prox)
		{
			if(g == lista && g == aSerRemovido)
			{
				lista = g.prox;
				//break;
			}
			if(g.prox == aSerRemovido)
			{
				g.prox = g.prox.prox;
				break;
			}
		}
	}
	
	private void insereNalistaComZeroPredecessores(Elo eloASerLido, Elo eloAInserir) 
	{
		Elo s;
		for(s = eloASerLido; s != null; s = s.prox)
		{
			if(s.prox == null)
			{
				s.prox = eloAInserir;
				s.prox.prox = null;
				break;
			}
		}
	}
}