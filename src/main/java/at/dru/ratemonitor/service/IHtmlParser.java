package at.dru.ratemonitor.service;

import at.dru.ratemonitor.data.ConversionRate;
import org.jsoup.nodes.Document;

import java.util.function.Function;

public interface IHtmlParser {

    Function<Document, ConversionRate> getMapper(String baseUrl);

}
