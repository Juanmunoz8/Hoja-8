import simpy
import random
from hospital1 import Hospital

def paciente(env, nombre, hospital):
    prioridad = random.randint(1, 3)  # 1 = más urgente, 3 = menos
    llegada = env.now
    print(f"{nombre} llega al hospital a las {llegada:.2f} con prioridad {prioridad}")

    # Consultorio
    with hospital.consultorios.request(priority=prioridad) as req:
        yield req
        print(f"{nombre} entra al consultorio a las {env.now:.2f}")
        yield env.timeout(random.uniform(5, 10))  # tiempo consulta

    # Laboratorio
    with hospital.laboratorios.request(priority=prioridad) as req:
        yield req
        print(f"{nombre} entra al laboratorio a las {env.now:.2f}")
        yield env.timeout(random.uniform(10, 15))  # tiempo laboratorio

    # Rayos X
    with hospital.rayos_x.request(priority=prioridad) as req:
        yield req
        print(f"{nombre} entra a rayos X a las {env.now:.2f}")
        yield env.timeout(random.uniform(7, 12))  # tiempo rayos X

    salida = env.now
    print(f"{nombre} termina su atención a las {salida:.2f}")
    return salida - llegada

def generador_pacientes(env, hospital, tasa_llegadas, cantidad):
    for i in range(cantidad):
        yield env.timeout(random.expovariate(1.0 / tasa_llegadas))
        env.process(paciente(env, f"Paciente {i+1}", hospital))

def simular(num_consultorios, num_laboratorios, num_rayos_x, tasa_llegadas, cantidad_pacientes, tiempo_simulacion):
    env = simpy.Environment()
    hospital = Hospital(env, num_consultorios, num_laboratorios, num_rayos_x)
    env.process(generador_pacientes(env, hospital, tasa_llegadas, cantidad_pacientes))
    env.run(until=tiempo_simulacion)

# Ejecutar simulación
if __name__ == "__main__":
    simular(
        num_consultorios=2,
        num_laboratorios=1,
        num_rayos_x=1,
        tasa_llegadas=5,           # promedio 1 paciente cada 5 minutos
        cantidad_pacientes=10,
        tiempo_simulacion=1000     # duración total de la simulación
    )
