package bg.tasky.TaskManagement.services;

import bg.tasky.TaskManagement.dtos.CardDto;
import bg.tasky.TaskManagement.entities.CardEntity;
import bg.tasky.TaskManagement.entities.ListEntity;
import bg.tasky.TaskManagement.entities.TaskEntity;
import bg.tasky.TaskManagement.mappers.CardMapper;
import bg.tasky.TaskManagement.repositories.CardRepo;
import bg.tasky.TaskManagement.repositories.TaskRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CardService {

    private final CardRepo cardRepo;
    private final CardMapper cardMapper;
    private final ListService listService;
    private final TaskRepo taskRepo;

    public CardService(CardRepo cardRepo, CardMapper cardMapper, ListService listService, TaskRepo taskRepo) {
        this.cardRepo = cardRepo;
        this.cardMapper = cardMapper;
        this.listService = listService;
        this.taskRepo = taskRepo;
    }

    public CardDto createCard(String listTitle, String boardKey, CardDto cardDto) {
        ListEntity listEntity = listService.getListEntityByTitle(boardKey, listTitle);

        CardEntity cardEntity = cardMapper.convertDtoToEntity(cardDto);
        String cardTitle = listEntity.getTitle();

        boolean exists = cardRepo.existsByTitleAndList(cardTitle, listEntity);
        if (exists) {
            throw new IllegalArgumentException("A card with this title already exists on the list: " + cardTitle);
        }

        cardEntity.setList(listEntity);
        cardEntity.setTasks(new HashSet<>());
        cardEntity = cardRepo.save(cardEntity);

        return cardMapper.convertEntityToDto(cardEntity);
    }

    public CardDto getCardByTitle(String boardKey, String listTitle, String cardTitle) {
        ListEntity listEntity = listService.getListEntityByTitle(boardKey, listTitle);

        CardEntity cardEntity = cardRepo.findByTitleAndList(cardTitle, listEntity);

        return cardMapper.convertEntityToDto(cardEntity);
    }

    public CardEntity getCardEntityByTitle(String boardKey, String listTitle, String cardTitle) {
        ListEntity listEntity = listService.getListEntityByTitle(boardKey, listTitle);

        CardEntity cardEntity = cardRepo.findByTitleAndList(cardTitle, listEntity);

        return cardEntity;
    }


    public List<CardDto> getCardsByList(String boardKey, String listTitle) {
        ListEntity listEntity = listService.getListEntityByTitle(boardKey, listTitle);
        List<CardEntity> cardEntities = cardRepo.findAllByList(listEntity);
        return cardEntities.stream()
                .map(cardMapper::convertEntityToDto)
                .toList();
    }

    public CardDto updateCardTitle(String boardKey, String listTitle, String oldTitle, CardDto cardDto) {
        ListEntity listEntity = listService.getListEntityByTitle(boardKey, listTitle);
        CardEntity cardEntity = cardRepo.findByTitleAndList(oldTitle, listEntity);
        CardEntity newEntity = cardMapper.convertDtoToEntity(cardDto);

        if (newEntity.getTitle() != null && !newEntity.getTitle().isEmpty()) {
            cardEntity.setTitle(newEntity.getTitle());
        }
        if (newEntity.getDescription() != null && !newEntity.getDescription().isEmpty()) {
            cardEntity.setDescription(newEntity.getDescription());
        }

        cardEntity = cardRepo.save(cardEntity);

        return cardMapper.convertEntityToDto(cardEntity);
    }


    public CardDto deleteCard(String boardKey, String listTitle, String cardTitle) {
        ListEntity listEntity = listService.getListEntityByTitle(boardKey, listTitle);
        CardEntity cardEntity = cardRepo.findByTitleAndList(cardTitle, listEntity);

        List<TaskEntity> tasks = taskRepo.findAllByCard(cardEntity);
        for (TaskEntity task : tasks) {
            task.setCard(null);
            taskRepo.delete(task);
        }

        cardRepo.delete(cardEntity);
        return cardMapper.convertEntityToDto(cardEntity);
    }
}
