import React, { useEffect, useState } from 'react';
import { AnimalDTO } from '../dto/AnimalDTO';
import AnimalsService from '../services/AnimalsService';

const AnimalList = () => {
  const [animals, setAnimals] = useState<AnimalDTO[]>([]);
  const [newAnimal, setNewAnimal] = useState<AnimalDTO>({ name: '', age: 1, species: '', status: '' });
  const [editAnimalId, setEditAnimalId] = useState<number | null>(null);
  const [editedAnimal, setEditedAnimal] = useState<AnimalDTO | null>(null);

  useEffect(() => {
    fetchAnimals();
  }, []);

  const fetchAnimals = () => {
    AnimalsService.getAllAnimals()
      .then(response => setAnimals(response.data))
      .catch(error => {
        if (error.response && error.response.data && error.response.data.message) {
          alert(`Error: ${error.response.data.message}`);
        } else {
          alert('An unexpected error occurred while getting animals.');
        }
      });
  };

  const addAnimal = () => {
    AnimalsService.createAnimal(newAnimal)
      .then(response => {
        // Add the new animal to the list and reset the form
        setAnimals([...animals, response.data]);
        setNewAnimal({ name: '', age:1, species: '', status: '' });
      })
      .catch(error => {
        if (error.response && error.response.data && error.response.data.message) {
          alert(`Error: ${error.response.data.message}`);
        } else {
          alert('An unexpected error occurred while adding an animal.');
        }
      });
  };


  const handleEdit = (animal: AnimalDTO) => {
    if(animal.id != undefined) {
    setEditAnimalId(animal.id); // Set the ID of the animal being edited
    setEditedAnimal({ ...animal });
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>, field: keyof AnimalDTO) => {
    setEditedAnimal(prev => ({ ...prev!, [field]: event.target.value }));
  };

  const handleUpdate = (id: number) => {
    if (editedAnimal) {
      AnimalsService.updateAnimal(id, editedAnimal)
        .then(() => {
          const updatedAnimals = animals.map(animal => animal.id === id ? editedAnimal : animal);
          setAnimals(updatedAnimals);
          setEditAnimalId(null);
          setEditedAnimal(null);
        })
        .catch(error => {
          if (error.response && error.response.data && error.response.data.message) {
            alert(`Error: ${error.response.data.message}`);
          } else {
            alert('An unexpected error occurred while updating the animal.');
          }
        });
    }
  };

  const handleDelete = (id: number) => {
    AnimalsService.deleteAnimal(id).then(() => fetchAnimals()).catch(error => {
      if (error.response && error.response.data && error.response.data.message) {
        alert(`Error: ${error.response.data.message}`);
      } else {
        alert('An unexpected error occurred while deleting the animal.');
      }
    })
  }

  const handleCancel = () => {
    setEditAnimalId(null);
    setEditedAnimal(null);
  };

  return (
    <div>
      <h2>Add Animal</h2>
      {/* Form for adding a new animal */}
      <form onSubmit={(e) => {
        e.preventDefault();
        addAnimal();
      }}>
        <label>Name: </label>
        <input
          type="text"
          placeholder="Name"
          value={newAnimal.name}
          onChange={(e) => setNewAnimal({ ...newAnimal, name: e.target.value })}
        />
        <br></br>
        <label>Age: </label>
        <input
          type="number"
          placeholder="Age"
          value={newAnimal.age}
          onChange={(e) => setNewAnimal({ ...newAnimal, age: Number(e.target.value) })}
        />
        <br></br>
        <label>Species: </label>
        <input
          type="text"
          placeholder="Species"
          value={newAnimal.species}
          onChange={(e) => setNewAnimal({ ...newAnimal, species: e.target.value })}
        />
        <br></br>
        <label>Status: </label>
        <input
          type="text"
          placeholder="Status"
          value={newAnimal.status}
          onChange={(e) => setNewAnimal({ ...newAnimal, status: e.target.value })}
        />
        <br></br>
        <button type="submit">Add Animal</button>
      </form>
      <h2>Animals</h2>
      <ul>
        {animals.map(animal => (
          <li key={animal.id}>
            {editAnimalId === animal.id ? (
              <div>
                <input type="text" value={editedAnimal?.name} onChange={(e) => handleInputChange(e, 'name')} />
                <input type="number" value={editedAnimal?.age} onChange={(e) => handleInputChange(e, 'age')} />
                <input type="text" value={editedAnimal?.species} onChange={(e) => handleInputChange(e, 'species')} />
                <input type="text" value={editedAnimal?.status} onChange={(e) => handleInputChange(e, 'status')} />
                <button onClick={() => handleUpdate(animal.id!)}>Save</button>
                <button onClick={handleCancel}>Cancel</button>
              </div>
            ) : (
              <div>
                {animal.name} - {animal.age} - {animal.species} - {animal.status}
                <button onClick={() => handleEdit(animal)}>Edit</button>
                <button onClick={() => handleDelete(animal.id!)}>Delete</button>
              </div>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default AnimalList;
