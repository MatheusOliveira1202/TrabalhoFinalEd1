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
		//cria as int's que iram receber os numeros que estão no documento
		int x, y;
		
		//lógica de leitura de arquivo
		File file = new File(nomeEntrada);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String textoDoDocumento = null, linha;
			//a cada linha lida ele executa o código abaixo
			while((linha = br.readLine()) != null){
				textoDoDocumento += "\n" + linha;
				//limpa os espaços vazios da linha, por exemplo de "1 < 2" pra "1<2"
				linha = linha.replaceAll(" ", "");
				//pega os valores de 'x' e 'y' no arquivo de texto e converte para int
				x = Integer.parseInt(linha.substring(0, linha.indexOf("<")));
				y = Integer.parseInt(linha.substring(linha.indexOf("<") + 1));
				//faz a verificação dos elementos do par para ver se existem na lista e adicionarem na lista se ainda não existirem
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
	
	//faz a verificação do par
	private void verificaPar(int x, int y) 
	{	
		//verifica se existe 'x' na lista e se não existe ele chama o método de adicionar que adiciona no final da lista
		if(!existeNaLista(x))
		{
			insereNaLista(x);
		}
		//verifica se existe 'y' na lista e se não existe ele chama o método de adicionar que adiciona no final da lista
		if(!existeNaLista(y))
		{
			insereNaLista(y);
		}
		
		//cria a referencia dos elos 'x' e 'y'
		Elo eloX;
		Elo eloY;
		
		//inicializa os elos fazendo uma busca por chave com as chaves que foram passadas no métodos
		eloX = buscaEloPorChave(x);
		eloY = buscaEloPorChave(y);
		
		/*se o eloX não tiver uma lista de sucessores ele cria uma lista de sucessores sendo o primeiro sucessor o elemento
		 *  'y' e incrementa o contador de y*/
		if(eloX.listaSuc == null)
		{
			eloX.listaSuc = new EloSuc(eloY, null);
			eloY.contador += 1;
		}
		/*se ele já tiver uma lista de sucessores ele apenas adiciona o 'y' na sua lista de sucessores e incrementa o
		 * contador de 'y'*/
		else
		{
			EloSuc antigo = eloX.listaSuc;
			eloX.listaSuc = new EloSuc(eloY, antigo);
			eloY.contador += 1;
		}
	}
	
	//método que percorre a lista e busca um elemento da lista pelo valor da chave que é dado
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
	
	//método para inserir a lista o elemento
	private void insereNaLista(int numeroAInserir) 
	{
		/*se a lista não está vazia ele vai percorrer a lista e vai adicionar o elemento no final da lista, adicionando o elemento quando
		é checado o último elemento, adicionamos um valor à 'n' e passamos um contador zerado pois está sendo adicionado 
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
		/*caso a lista esteja vazia ele adiciona o elemento na primeira posição da lista e adiciona um valor à 'n' também
		 * e passamos um contador zerado pois o elo está sendo criado agora*/
		else
		{
			prim = new Elo(numeroAInserir, 0, null, null);
			n += 1;
		}
	}
	
	/*método para verificar se existe um elo na lista fazendo a busca através da chave*/
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
	
	// Método para impressão do estado atual da estrutura de dados. 
	private void debug(Elo elo)
	{
		//string para guardar o valor dos sucessores que aserá adicionado no final do print line
		String sucessores = "";
		//EloSuc para verificar a lista de sucessores do elo que foi passado para o método
		EloSuc p = elo.listaSuc;
		//se a lista de sucessores já existir e não for nula, ele percorre toda a lista de sucessores e vai somando na string 
		if(elo.listaSuc != null)
		{
			for(p = elo.listaSuc; p != null; p = p.prox) 
			{
				sucessores += p.id.chave + " -> ";
			}
			sucessores += "NULL";
		}
		//caso a lista de sucessores seja nula, o valor da variável será nulo para que que não retorne nenhum valor nessa string 
		else 
		{
			sucessores = "NULL";
		}
		//faz o print line do elo com as informações necessárias e a string de sucessores que foi montada acima
		System.out.println(elo.chave + " predecessores: " + elo.contador + ", sucessores : " + sucessores);
	}
	
	/* Método responsável por executar o algoritmo. */
	public boolean executa()
	{
		//depois que já criou a lista inteira, lê a lista e faz o debug de cada nó
		
		Elo d;
		for(d = prim; d != null; d = d.prox) 
		{
			debug(d);
		}
		
		System.out.println("");
		System.out.println("Ordenação Topológica");
		
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
				System.out.println("Conjunto não é parcialmente ordenado.");
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