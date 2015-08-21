package br.ufjf.app.ui;

/** Responde a cliques em um item de uma lista
 * Created by cgco on 21/08/15.
 */
public interface OnItemSelecionadoListener<T> {
    /**
     * Chamado quando um item e clicado
     * @param item Item cllicado
     */
    void onItemSelecionado(T item);
}
