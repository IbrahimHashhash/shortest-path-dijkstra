# ✈️ Shortest Path Dijkstra

A JavaFX application that visualizes the shortest flight route between capital cities using Dijkstra’s algorithm. The app provides users with optimal paths based on time, cost, or distance, presented with a clean and visually appealing UI designed using CSS.

---

## 🚀 Features

- 🔍 Find the shortest flight route between any two capital cities
- ⏱️ Optimize by time, cost, or distance
- 🗺️ Cities are placed based on real latitude and longitude
- 🎨 Modern, responsive UI built with JavaFX and styled with CSS
- 🧠 Uses a custom Dijkstra’s algorithm implementation
- 📊 Shows detailed results including total time, cost, and distance

---

## 🛠 Technologies Used

- Java
- JavaFX
- CSS
- Custom Data Structures:
  - HashTable
  - SinglyLinkedList
  - Graph

---

## 🧠 Algorithm: Dijkstra's Algorithm

This project implements **Dijkstra’s algorithm** to determine the shortest path between two cities in a weighted graph.

### Step-by-step:

1. All cities (vertices) are initialized with a "weight" of infinity except the source, which is set to `0`.
2. At each step, the algorithm selects the unknown city with the smallest known weight.
3. It updates its neighboring cities if a shorter path is found through it.
4. This process repeats until the destination is reached or all nodes are visited.
5. The path is reconstructed by tracing back from the destination using `previousVertex`.

### Key Code Snippet

```java
double edgeCost = switch (costType.toLowerCase()) {
    case "time" -> edge.getTime();
    case "cost" -> edge.getCost();
    default -> edge.getDistance();
};

double newDistance = currentEntry.weight + edgeCost;
if (newDistance < neighborEntry.weight) {
    neighborEntry.weight = newDistance;
    neighborEntry.previousVertex = currentVertex;
}
```

![image](https://github.com/user-attachments/assets/e6ef15ca-39c7-4611-a8ff-11d9ca7cdf60)
![image](https://github.com/user-attachments/assets/eb78518e-3423-4f09-ad3a-7fbdae6906da)
![image](https://github.com/user-attachments/assets/e55966d5-c3c5-449d-ae42-8a95ed464378)
