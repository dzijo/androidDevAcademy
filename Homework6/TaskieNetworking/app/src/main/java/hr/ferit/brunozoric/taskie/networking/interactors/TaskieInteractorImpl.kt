package hr.ferit.brunozoric.taskie.networking.interactors

import hr.ferit.brunozoric.taskie.model.BackendTask
import hr.ferit.brunozoric.taskie.model.request.*
import hr.ferit.brunozoric.taskie.model.response.*
import hr.ferit.brunozoric.taskie.networking.TaskieApiService
import retrofit2.Callback

class TaskieInteractorImpl(private val apiService: TaskieApiService) : TaskieInteractor {

    override fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>) {
        apiService.getTasks().enqueue(taskieResponseCallback)
    }

    override fun getTaskById(request: GetTaskByIdRequest, getTaskByIdResponse: Callback<BackendTask>) {
        apiService.getTaskById(request.taskId).enqueue(getTaskByIdResponse)
    }

    override fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>) {
        apiService.register(request).enqueue(registerCallback)
    }

    override fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>) {
        apiService.login(request).enqueue(loginCallback)
    }

    override fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>) {
        apiService.save(request).enqueue(saveCallback)
    }

    override fun edit(request: EditTaskRequest, editCallback: Callback<BackendTask>) {
        apiService.edit(request).enqueue(editCallback)
    }

    override fun delete(request: DeleteTaskRequest, deleteCallback: Callback<BasicResponse>) {
        apiService.delete(request.taskId).enqueue(deleteCallback)
    }
}