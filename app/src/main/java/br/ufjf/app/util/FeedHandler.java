package br.ufjf.app.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Stack;

import br.ufjf.app.model.news.Article;
import br.ufjf.app.model.news.Feed;

public class FeedHandler extends DefaultHandler {
    private Stack<String> elementStack = new Stack<>();
    private StringBuilder stringBuilder;

    private Feed feed;
    private Article articleAtual;

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        elementStack.push(qName);

        stringBuilder = new StringBuilder();

        if (qName.equalsIgnoreCase("item")) {
            articleAtual = new Article();
        } else if (qName.equalsIgnoreCase("channel")) {
            feed = new Feed();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        String value = stringBuilder.toString().trim();

        if (qName.equalsIgnoreCase("item")) {
            if (feed.getArticles() == null)
                feed.setArticles(new ArrayList<Article>());
            feed.getArticles().add(articleAtual);
            articleAtual = null;
        } else if (currentElementParent() != null
                && currentElementParent().equalsIgnoreCase("channel")) {

            if (currentElement().equalsIgnoreCase("title"))
                feed.setTitulo(value);

            if (currentElement().equalsIgnoreCase("link"))
                feed.setLink(value);
        } else if (articleAtual != null) {
            if (currentElement().equalsIgnoreCase("title"))
                articleAtual.setTitle(value);

            if (currentElement().equalsIgnoreCase("link"))
                articleAtual.setLink(value);

            if (currentElement().equalsIgnoreCase("pubDate")) {
                articleAtual.setDate(value);
            }

            if (currentElement().equalsIgnoreCase("description"))
                articleAtual.setContent(value.replaceAll("<img.*?/>", "")/*todo retiar o 'Leia mais' .replaceAll("<a .*?>Leia mais <span class=\"meta-nav\">&#8594;</span></a>", "")*/);
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
