import axios from 'axios';
import { AnimalDTO } from '../dto/AnimalDTO';

const API_URL = 'http://localhost:8080/animals';

const getAllAnimals = () => axios.get<AnimalDTO[]>(API_URL);
const getAnimalById = (id: number) => axios.get<AnimalDTO>(`${API_URL}/${id}`);
const createAnimal = (data: AnimalDTO) => axios.post<AnimalDTO>(API_URL, data);
const updateAnimal = (id: number, data: AnimalDTO) => {console.log(`${API_URL}/${id}`, data); return axios.put<AnimalDTO>(`${API_URL}/${id}`, data);}
const deleteAnimal = (id: number) => axios.delete(`${API_URL}/${id}`);

export default {
  getAllAnimals,
  getAnimalById,
  createAnimal,
  updateAnimal,
  deleteAnimal
};
