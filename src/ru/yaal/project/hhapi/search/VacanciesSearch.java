package ru.yaal.project.hhapi.search;

import ru.yaal.project.hhapi.HhConstants;
import ru.yaal.project.hhapi.loader.ContentLoader;
import ru.yaal.project.hhapi.loader.IContentLoader;
import ru.yaal.project.hhapi.parser.IParser;
import ru.yaal.project.hhapi.parser.VacanciesParser;
import ru.yaal.project.hhapi.search.parameter.ISearchParameter;
import ru.yaal.project.hhapi.search.parameter.SearchParamNames;
import ru.yaal.project.hhapi.search.parameter.SearchParameterBox;

import java.util.List;
import java.util.Map;

public class VacanciesSearch implements ISearch<VacanciesList> {
    private IContentLoader loader = new ContentLoader();
    private SearchParameterBox parameterBox = new SearchParameterBox();

    @Override
    public VacanciesList search() throws SearchException {
        try {
            putParametersToLoader();
            String content = loader.loadContent(HhConstants.VACANCIES_URL);
            IParser<VacanciesList> parser = new VacanciesParser();
            return parser.parse(content);
        } catch (Exception e) {
            throw new SearchException(e);
        }
    }

    private void putParametersToLoader() {
        Map<SearchParamNames, List<String>> paramMap = parameterBox.getParameterMap();
        for (SearchParamNames key : paramMap.keySet()) {
            for (String value : paramMap.get(key)) {
                loader.addParam(key.getName(), value);
            }
        }
    }

    @Override
    public VacanciesSearch addParameter(ISearchParameter searchParameter) throws SearchException {
        parameterBox.addParameter(searchParameter.getSearchParameters());
        return this;
    }
}
