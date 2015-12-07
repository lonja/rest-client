package api;

import model.*;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import java.time.LocalDate;

public interface API {
    //Получить все операции
    //+
    @GET("operations")
    Call<Operation[]> getOperations();

    //Получить всех мастеров
    //+
    @GET("masters")
    Call<Master[]> getMasters();

    //Получить ведомость расхода деталей заданного мастера за определенный период
    //+
    @GET("masters/details")
    Call<MasterDetailsExpense[]> getMasterDetailsExpense(
            @Query("id") int masterId,
            @Query("startDate") LocalDate startDate,
            @Query("expDate") LocalDate expDate);

    //Получить ведомость расхода деталей за определенный период
    //+
    @GET("details/expense")
    Call<DetailsExpense[]> getDetailExpense(
            @Query("startDate") LocalDate startDate,
            @Query("expDate") LocalDate finishDate);

    //Получить ведомость выполненных операций заданным мастером
    @GET("masters/operations")
    Call<MasterOperations[]> getMasterOperations(
            @Query("id") int masterId,
            @Query("startDate") LocalDate startDate,
            @Query("expDate") LocalDate finishDate);

    //Получить суммарную стоимость операций за определенный период
    //+
    @GET("masters/operations/sum")
    Call<MasterOperationsSum> getMasterOperationsSum(@Query("startDate") LocalDate startDate,
                                                     @Query("expDate") LocalDate expDate);

    //Можно ли приступить к выполнению заданной операции
    //+
    @POST("operations/new")
    Call<NewOperation> startNewOperation(
            @Query("operationId") int operationId,
            @Query("startDate") LocalDate startDate);

    //Добавление новой операции
    @POST("operations")
    Call<Operation> createNewOperation(
            @Query("title") String title,
            @Query("duration") String duration,
            @Query("price") Double price);

    @POST("operationsmasters")
    Call<OperationMaster> createNewRepair(
            @Query("title") String title,
            @Query("duration") String duration,
            @Query("price") Double price,
            @Query("name") String name,
            @Query("surname") String surname,
            @Query("patronymic") String patronymic,
            @Query("startDate") LocalDate startDate);
}