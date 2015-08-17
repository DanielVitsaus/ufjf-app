package br.ufjf.app.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Stack;

import br.ufjf.app.model.noticias.Artigo;
import br.ufjf.app.model.noticias.Feed;

/**
 * Parser para um feed RSS
 */
public class FeedHandler extends DefaultHandler {
    private Stack<String> elementStack = new Stack<>();
    private StringBuilder stringBuilder;

    private Feed feed;
    private Artigo artigoAtual;

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        elementStack.push(qName);

        stringBuilder = new StringBuilder();

        if (qName.equalsIgnoreCase("item")) {
            artigoAtual = new Artigo();
        } else if (qName.equalsIgnoreCase("channel")) {
            feed = new Feed();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        String value = stringBuilder.toString().trim();

        if (qName.equalsIgnoreCase("item")) {
            if (feed.getArtigos() == null)
                feed.setArtigos(new ArrayList<Artigo>());
            feed.getArtigos().add(artigoAtual);
            artigoAtual = null;
        } else if (currentElementParent() != null
                && currentElementParent().equalsIgnoreCase("channel")) {

            if (currentElement().equalsIgnoreCase("title"))
                feed.setTitulo(value);

            if (currentElement().equalsIgnoreCase("link"))
                feed.setLink(value);
        } else if (artigoAtual != null) {
            if (currentElement().equalsIgnoreCase("title"))
                artigoAtual.setTitle(value);

            if (currentElement().equalsIgnoreCase("link"))
                artigoAtual.setLink(value);

            if (currentElement().equalsIgnoreCase("pubDate")) {
                artigoAtual.setDate(value);
            }

            if (currentElement().equalsIgnoreCase("description"))
                artigoAtual.setContent(value.replaceAll("<img.*?/>", "")/*todo retiar o 'Leia mais' .replaceAll("<a .*?>Leia mais <span class=\"meta-nav\">&#8594;</span></a>", "")*/);
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
