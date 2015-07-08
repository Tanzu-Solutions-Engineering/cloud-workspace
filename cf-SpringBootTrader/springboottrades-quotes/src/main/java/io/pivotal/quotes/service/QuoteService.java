package io.pivotal.quotes.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.quotes.domain.CompanyInfo;
import io.pivotal.quotes.domain.Quote;
import io.pivotal.quotes.exception.SymbolNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * A service to retrieve Company and Quote information.
 * 
 * @author David Ferreira Pinto
 *
 */
@Service
public class QuoteService {

	//TODO: change to get URL from Cloud service?
	//TODO: add hystrix!
    private static String json = "{\"Status\":\"SUCCESS\",\"Name\":\"Credit Suisse Group AG (CS)\",\"Symbol\":\"CS\",\"LastPrice\":28.82,\"Change\":-0.980000000000004,\"ChangePercent\":-1.46706586826348,\"Timestamp\":\"Wed Jul 8 09:42:02 UTC-04:00 2015\",\"MSDate\":42193.4041898168,\"MarketCap\":244263746340,\"Volume\":73028,\"ChangeYTD\":28.58,\"ChangePercentYTD\":5.17737296260785,\"High\":28.16,\"Low\":28.82,\"Open\":28}";

    private static final String QUOTE_URL = "http://dev.markitondemand.com/Api/v2/Quote/json?symbol={symbol}";
	private static final String COMPANY_URL = "http://dev.markitondemand.com/Api/v2/Lookup/json?input={name}"; 
	
	private static final Logger logger = LoggerFactory
			.getLogger(QuoteService.class);
	
	private RestTemplate restTemplate = new RestTemplate();
	/**
	 * Retrieves an up to date quote for the given symbol.
	 * 
	 * @param symbol The symbol to retrieve the quote for.
	 * @return The quote object or null if not found.
	 * @throws SymbolNotFoundException 
	 */
	public Quote getQuote(String symbol) throws SymbolNotFoundException {
		logger.debug("QuoteService.getQuote: retrieving quote for: " + symbol);
		Map<String, String> params = new HashMap<String, String>();
	    params.put("symbol", symbol);

        if (symbol.equalsIgnoreCase("CS")) {
            try {
                return new ObjectMapper().readValue(json,Quote.class);
            } catch (IOException e) {
                logger.error("QuoteService.getQuote: retrieved quote: ");
            }
        }
	    Quote quote = restTemplate.getForObject(QUOTE_URL, Quote.class, params);
        logger.debug("QuoteService.getQuote: retrieved quote: " + quote);
        
        if (quote.getSymbol() ==  null) {
        	throw new SymbolNotFoundException("Symbol not found: " + symbol);
        }
		return quote;
	}
	
	/**
	 * Retrieves a list of CompanyInfor objects.
	 * Given the name parameters, the return list will contain objects that match the search both
	 * on company name as well as symbol.
	 * @param name The search parameter for company name or symbol.
	 * @return The list of company information.
	 */
	public List<CompanyInfo> getCompanyInfo(String name) {
		logger.debug("QuoteService.getCompanyInfo: retrieving info for: " + name);
		Map<String, String> params = new HashMap<String, String>();
	    params.put("name", name);
	    CompanyInfo[] companies = restTemplate.getForObject(COMPANY_URL, CompanyInfo[].class, params);
	    logger.debug("QuoteService.getCompanyInfo: retrieved info: " + companies);
		return Arrays.asList(companies);
	}
}
