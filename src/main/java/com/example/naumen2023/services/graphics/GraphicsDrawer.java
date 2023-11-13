package com.example.naumen2023.services.graphics;

import com.example.naumen2023.models.entities.AreaEntity;
import com.example.naumen2023.models.entities.EmployerEntity;
import com.example.naumen2023.models.entities.ProgrammingLanguageEntity;
import com.example.naumen2023.models.entities.VacancyHHruEntity;
import com.example.naumen2023.models.enums.GraphicsTitleAndFileName;
import com.example.naumen2023.models.enums.GraphicsType;
import com.example.naumen2023.models.enums.ProgrammingLanguageName;
import com.example.naumen2023.repositories.AreaRepository;
import com.example.naumen2023.repositories.EmployerRepository;
import com.example.naumen2023.repositories.ProgrammingLanguageRepository;
import com.example.naumen2023.services.graphics.interfaces.AllProgrammingLanguagesChartDataMapperInterface;
import com.example.naumen2023.services.graphics.interfaces.GraphicsCreatorInterface;
import com.example.naumen2023.services.graphics.interfaces.ProgrammingLanguageChartDataMapper;
import com.example.naumen2023.services.graphics.mapper.AllProgrammingLanguageChartDataMapper;
import com.example.naumen2023.services.graphics.mapper.AreaChartDataMapper;
import com.example.naumen2023.services.graphics.interfaces.ChartDataMapper;
import com.example.naumen2023.services.graphics.mapper.EmployerChartDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Tribushko Danil
 * @since 05.11.2023
 * <
 * Класс для отрисовки всех графиков и сохранение их в директорию
 */
@Service
public class GraphicsDrawer {
    private final GraphicsCreatorInterface graphicsCreatorService;
    private final AreaRepository areaRepository;
    private final EmployerRepository employerRepository;
    private final ProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    public GraphicsDrawer(AreaRepository areaRepository,
                          EmployerRepository employerRepository,
                          ProgrammingLanguageRepository programmingLanguageRepository) {
        this.areaRepository = areaRepository;
        this.employerRepository = employerRepository;
        this.programmingLanguageRepository = programmingLanguageRepository;
        graphicsCreatorService = new GraphicsCreatorService();
    }

    /**
     * Отрисовка всех граффиков
     */
    @Async
    public void drawAllGraphics() {
        drawAreas();
        drawEmployersGraphics();
        drawAllProgrammingLanguageGraphics();
        for (ProgrammingLanguageName programmingLanguageName : ProgrammingLanguageName.values()) {
            drawAreas(programmingLanguageName);
            drawEmployersGraphics(programmingLanguageName);
            drawProgrammingLanguageGraphics(programmingLanguageName);
        }
    }

    /**
     * Отрисовка гарфиков для регионов
     */
    @Async
    protected void drawAreas() {
        //Получаем датасет
        ChartDataMapper dataMapper = new AreaChartDataMapper(areaRepository.findAll());
        ChartDrawerService service = new ChartDrawerService();
        //Создаем диаграмму содержащую распределение вакансий среди регионов
        graphicsCreatorService.createGraphics(
                service.createPieChart(
                        GraphicsTitleAndFileName
                                .DISTRIBUTION_OF_VACANCIES_FROM_AREAS
                                .getTitle(),
                        dataMapper.createDatasetCountVacancies()

                ),
                GraphicsType.AREAS,
                GraphicsTitleAndFileName
                        .DISTRIBUTION_OF_VACANCIES_FROM_AREAS
                        .getFileName());
        //Создаем гистограмму отображающую города с наибольшим количеством вакансий
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .TOP_10_AREAS_BY_COUNT_VACANCIES
                                .getTitle(),
                        "Количество вакансий",
                        "Города",
                        dataMapper.createDatasetMostPopular()
                ),
                GraphicsType.AREAS,
                GraphicsTitleAndFileName
                        .TOP_10_AREAS_BY_COUNT_VACANCIES
                        .getFileName()
        );
        //Создаем гистограмму отображаю города с наибольшой средней зарплатой
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .TOP_10_AREAS_BY_MIDDLE_SALARY
                                .getTitle(),
                        "Зарплата",
                        "Города",
                        dataMapper.createDatasetByMiddleSalary()
                ),
                GraphicsType.AREAS,
                GraphicsTitleAndFileName
                        .TOP_10_AREAS_BY_MIDDLE_SALARY
                        .getFileName()
        );
    }

    /**
     * Отрисовка графиков для регионов по определенному языку программирования
     *
     * @param programmingLanguageName название языка программирования
     */
    @Async
    protected void drawAreas(ProgrammingLanguageName programmingLanguageName) {
        //Получаем датасет
        ChartDataMapper dataMapper = new AreaChartDataMapper(
                getAreasByProgrammingLanguage(programmingLanguageName)
        );
        ChartDrawerService service = new ChartDrawerService();
        String name = programmingLanguageName.getName();
        //Создаем диаграмму содержащую распределение вакансий среди регионов для определенного
        // языка программирования
        graphicsCreatorService.createGraphics(
                service.createPieChart(
                        GraphicsTitleAndFileName
                                .DISTRIBUTION_OF_VACANCIES_FROM_AREAS
                                .getTitle() + " " + name,
                        dataMapper.createDatasetCountVacancies()

                ),
                GraphicsType.AREAS,
                "/" + name.toLowerCase() + "/" +
                        GraphicsTitleAndFileName
                                .DISTRIBUTION_OF_VACANCIES_FROM_AREAS
                                .getFileName() + "_" + name.toLowerCase()
        );
        // Создаем гистограмму содержащую города с наибольшим количеством вакансий
        // по определенному языку программирования
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .TOP_10_AREAS_BY_COUNT_VACANCIES
                                .getTitle() + " " + name,
                        "Количество вакансий",
                        "Города",
                        dataMapper.createDatasetMostPopular()
                ),
                GraphicsType.AREAS,
                "/" + name.toLowerCase() + "/" +
                        GraphicsTitleAndFileName
                                .TOP_10_AREAS_BY_COUNT_VACANCIES
                                .getFileName() + "_" + name.toLowerCase()
        );
        // Создаем гистограмму содержащую города с наибольшей средней зарплатой
        // по определенному языку программирования
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .TOP_10_AREAS_BY_MIDDLE_SALARY.getTitle() + " " +
                                name.toLowerCase(),
                        "Зарплата",
                        "Города",
                        dataMapper.createDatasetByMiddleSalary()
                ),
                GraphicsType.AREAS,
                "/" + name.toLowerCase() + "/" +
                        GraphicsTitleAndFileName
                                .TOP_10_EMPLOYERS_BY_MIDDLE_SALARY
                                .getFileName() + "_" + name
        );
    }

    /**
     * Получение списка регионов в которых есть вакансии с определенным языком программирования
     *
     * @param programmingLanguageName название языка программирования
     * @return список с сущностями регионов
     */
    private List<AreaEntity> getAreasByProgrammingLanguage(
            ProgrammingLanguageName programmingLanguageName) {
        //Получаем сущность языка программирования из бд
        ProgrammingLanguageEntity programmingLanguage = programmingLanguageRepository
                .findByName(programmingLanguageName)
                .orElse(null);
        Set<AreaEntity> areas = new HashSet<>();
        if (programmingLanguage != null) {
            //Получаем список вакансий с языком программирования
            Set<VacancyHHruEntity> vacancies = programmingLanguage.getVacancies();
            for (VacancyHHruEntity vacancy : vacancies) {
                areas.add(vacancy.getArea());
            }
        }
        return new ArrayList<>(areas);
    }

    /**
     * Отрисовка графиков работодателей
     */
    @Async
    protected void drawEmployersGraphics() {
        //Получаем датасет
        ChartDataMapper dataMapper = new EmployerChartDataMapper(employerRepository.findAll());
        ChartDrawerService service = new ChartDrawerService();
        //Создаем диаграмму содержащую распределение вакансий среди работодателей
        graphicsCreatorService.createGraphics(
                service.createPieChart(
                        GraphicsTitleAndFileName
                                .DISTRIBUTION_OF_VACANCIES_FROM_EMPLOYERS
                                .getTitle(),
                        dataMapper.createDatasetCountVacancies()
                ),
                GraphicsType.EMPLOYERS,
                GraphicsTitleAndFileName
                        .DISTRIBUTION_OF_VACANCIES_FROM_EMPLOYERS
                        .getFileName()
        );
        //Создаем гистограмму содержащую работодателей с набольшим количеством вакансий
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .TOP_10_EMPLOYERS_BY_COUNT_VACANCIES
                                .getTitle(),
                        "Количество вакансий",
                        "Работодатели",
                        dataMapper.createDatasetMostPopular()
                ),
                GraphicsType.EMPLOYERS,
                GraphicsTitleAndFileName
                        .TOP_10_EMPLOYERS_BY_COUNT_VACANCIES
                        .getFileName()
        );
        //Создаем диаграмму содержащую работодателей с наибольшей средней зарплатой
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(GraphicsTitleAndFileName
                                .TOP_10_EMPLOYERS_BY_MIDDLE_SALARY
                                .getTitle(),
                        "Зарплата",
                        "Работодатели",
                        dataMapper.createDatasetByMiddleSalary()
                ),
                GraphicsType.EMPLOYERS,
                GraphicsTitleAndFileName.TOP_10_EMPLOYERS_BY_MIDDLE_SALARY.getFileName()
        );
    }

    /**
     * Отрисовка графиков для работодателей по определенному языку программирования
     *
     * @param programmingLanguageName название языка программирования
     */
    @Async
    protected void drawEmployersGraphics(ProgrammingLanguageName programmingLanguageName) {
        ChartDataMapper dataMapper = new EmployerChartDataMapper(getEmployersByProgrammingLanguage(
                programmingLanguageName)
        );
        ChartDrawerService service = new ChartDrawerService();
        String name = programmingLanguageName.getName();
        //Создаем диаграмму содержащую распределение вакансий среди работодателей по определенному
        // языку программирования
        graphicsCreatorService.createGraphics(
                service.createPieChart(
                        GraphicsTitleAndFileName
                                .DISTRIBUTION_OF_VACANCIES_FROM_EMPLOYERS
                                .getTitle() + " " + name,
                        dataMapper.createDatasetCountVacancies()
                ),
                GraphicsType.EMPLOYERS,
                "/" + name.toLowerCase() + "/" +
                        GraphicsTitleAndFileName
                                .DISTRIBUTION_OF_VACANCIES_FROM_EMPLOYERS
                                .getFileName() + "_" + name.toLowerCase()
        );
        //Создаем гистограмму содержащую работодателей с наибольшим количеством вакансий по
        // определенному языку программирования
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .TOP_10_EMPLOYERS_BY_COUNT_VACANCIES
                                .getTitle() + " " + name,
                        "Количество вакансий",
                        "Работодатели",
                        dataMapper.createDatasetMostPopular()),
                GraphicsType.EMPLOYERS,
                "/" + name.toLowerCase() + "/" +
                        GraphicsTitleAndFileName
                                .TOP_10_EMPLOYERS_BY_COUNT_VACANCIES
                                .getFileName() + "_" + name.toLowerCase()
        );
        //Создаем гистограмму содержащую работодателей с наибольшим количеством вакансий по
        // определенному языку программирования
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .TOP_10_EMPLOYERS_BY_MIDDLE_SALARY
                                .getTitle() + " " + name,
                        "Зарплата",
                        "Работодатели",
                        dataMapper.createDatasetByMiddleSalary()
                ),
                GraphicsType.EMPLOYERS,
                "/" + name.toLowerCase() + "/" +
                        GraphicsTitleAndFileName
                                .TOP_10_EMPLOYERS_BY_MIDDLE_SALARY
                                .getFileName() + "_" + name.toLowerCase()
        );
    }

    /**
     * Получаем список работодателей содержащих вакансии с определенным языком программирования
     *
     * @param programmingLanguageName название языка программирования
     * @return список работодателей
     */
    private List<EmployerEntity> getEmployersByProgrammingLanguage(
            ProgrammingLanguageName programmingLanguageName) {
        ProgrammingLanguageEntity programmingLanguage = programmingLanguageRepository
                .findByName(programmingLanguageName).orElse(null);
        Set<EmployerEntity> employers = new HashSet<>();
        if (programmingLanguage != null) {
            Set<VacancyHHruEntity> vacancies = programmingLanguage.getVacancies();
            for (VacancyHHruEntity vacancy : vacancies) {
                employers.add(vacancy.getEmployer());
            }
        }
        return new ArrayList<>(employers);
    }

    /**
     * Отрисовка графиков для определенного языка программирования
     *
     * @param programmingLanguageName название языка программирования
     */
    @Async
    protected void drawProgrammingLanguageGraphics(ProgrammingLanguageName programmingLanguageName) {
        ProgrammingLanguageEntity programmingLanguage = programmingLanguageRepository
                .findByName(programmingLanguageName)
                .orElse(null);
        if (programmingLanguage != null) {
            ChartDrawerService service = new ChartDrawerService();
            GraphicsCreatorService graphicsCreatorService = new GraphicsCreatorService();
            String name = programmingLanguageName.getName();
            graphicsCreatorService.createGraphics(
                    //Создаем chart
                    service.createCategoryChart(
                            GraphicsTitleAndFileName
                                    .MOST_POPULAR_SKILLS
                                    .getTitle() + " " + name,
                            "Количество навыков",
                            "Навыки",
                            ProgrammingLanguageChartDataMapper.createDatasetMostPopularSkills(
                                    programmingLanguageName,
                                    programmingLanguage.getVacancies()
                            )
                    ),
                    //Тип графика
                    GraphicsType.VACANCIES,
                    //Название файла
                    "/" + name.toLowerCase() + "/" +
                            GraphicsTitleAndFileName
                                    .MOST_POPULAR_SKILLS
                                    .getFileName() + "_" + name.toLowerCase()
            );
        }
    }

    @Async
    protected void drawAllProgrammingLanguageGraphics() {
        AllProgrammingLanguagesChartDataMapperInterface dataMapper = new AllProgrammingLanguageChartDataMapper(
                programmingLanguageRepository.findAll()
        );
        ChartDrawerService service = new ChartDrawerService();
        //Создание графика средней зарплаты
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .PROGRAMMING_LANGUAGES_BY_MIDDLE_SALARY
                                .getTitle(),
                        "Зарплата",
                        "Языки программирования",
                        dataMapper.createDatasetByMiddleSalary()
                ),
                GraphicsType.VACANCIES,
                GraphicsTitleAndFileName
                        .PROGRAMMING_LANGUAGES_BY_MIDDLE_SALARY
                        .getFileName()
        );
        //Создание графика самых популярных навыков
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .MOST_POPULAR_SKILLS
                                .getTitle(),
                        "Количество навыков",
                        "Навыки",
                        dataMapper.createDatasetCountSkills()
                ),
                GraphicsType.VACANCIES,
                GraphicsTitleAndFileName
                        .MOST_POPULAR_SKILLS
                        .getFileName()
        );
        //Создание графика количества вакансий среди языков программирования
        graphicsCreatorService.createGraphics(
                service.createCategoryChart(
                        GraphicsTitleAndFileName
                                .PROGRAMMING_LANGUAGES_BY_COUNT_VACANCIES
                                .getTitle(),
                        "Количество вакансий",
                        "Языки программирования",
                        dataMapper.createDatasetMostPopular()
                ),
                GraphicsType.VACANCIES,
                GraphicsTitleAndFileName
                        .PROGRAMMING_LANGUAGES_BY_COUNT_VACANCIES
                        .getFileName()
        );
        //Создание графика распределения вакансий
        graphicsCreatorService.createGraphics(
                service.createPieChart(
                        GraphicsTitleAndFileName
                                .DISTRIBUTION_OF_VACANCIES_FROM_PROGRAMMING_LANGUAGES
                                .getTitle(),
                        dataMapper.createDatasetCountVacancies()
                ),
                GraphicsType.VACANCIES,
                GraphicsTitleAndFileName
                        .DISTRIBUTION_OF_VACANCIES_FROM_PROGRAMMING_LANGUAGES
                        .getFileName()
        );
    }
}
