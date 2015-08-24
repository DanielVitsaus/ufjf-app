package br.ufjf.app.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Stack;

import br.ufjf.app.model.noticias.Noticia;
import br.ufjf.app.model.noticias.Feed;

/**
 * Parser para um feed RSS
 */
public class FeedHandler extends DefaultHandler {
    private Stack<String> elementStack = new Stack<>();
    private StringBuilder stringBuilder;

    private Feed feed;
    private Noticia noticiaAtual;

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        elementStack.push(qName);

        stringBuilder = new StringBuilder();

        if (qName.equalsIgnoreCase("item")) {
            noticiaAtual = new Noticia();
        } else if (qName.equalsIgnoreCase("channel")) {
            feed = new Feed();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        String value = stringBuilder.toString().trim();

        if (qName.equalsIgnoreCase("item")) {
            if (feed.getNoticias() == null)
                feed.setNoticias(new ArrayList<Noticia>());
            feed.getNoticias().add(noticiaAtual);
            noticiaAtual = null;
        } else if (currentElementParent() != null
                && currentElementParent().equalsIgnoreCase("channel")) {

            if (currentElement().equalsIgnoreCase("title"))
                feed.setTitulo(value);

            if (currentElement().equalsIgnoreCase("link"))
                feed.setLink(value);
        } else if (noticiaAtual != null) {
            if (currentElement().equalsIgnoreCase("title"))
                noticiaAtual.setTitulo(value);

            if (currentElement().equalsIgnoreCase("link"))
                noticiaAtual.setLink(value);

            if (currentElement().equalsIgnoreCase("pubDate")) {
                noticiaAtual.setData(value);
            }

            if (currentElement().equalsIgnoreCase("description"))
                noticiaAtual.setConteudo(value.replaceAll("<img.*?/>", "").replaceAll("Leia mais <span class=\"meta-nav\">&#8594;</span>", ""));
        }

        this.elementStack.pop();
    }

    public void characters(char ch[], int start, int length)
            throws SAXException {
        if (stringBuilder != null)
            for (int i = start; i < start + length; i++)
                stringBuilder.append(ch[i]);
    }

    private String currentElement() {
        return this.elementStack.peek();
    }

    private String currentElementParent() {
        if (this.elementStack.size() < 2)
            return null;
        return this.elementStack.get(this.elementStack.size() - 2);
    }

    public Feed getFeed() {
        return feed;
    }
}
