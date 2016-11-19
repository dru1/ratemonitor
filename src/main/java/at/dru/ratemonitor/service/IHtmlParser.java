package at.dru.ratemonitor.service;

import at.dru.ratemonitor.data.ConversionRate;
import org.jsoup.nodes.Document;

import javax.annotation.Nonnull;
import java.util.function.Function;

public interface IHtmlParser {

    @Nonnull
    Function<Document, ConversionRate> getMapper(@Nonnull  String baseUrl);

}
