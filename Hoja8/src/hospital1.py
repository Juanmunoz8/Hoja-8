import simpy

class Hospital:
    def __init__(self, env, num_consultorios, num_laboratorios, num_rayos_x):
        self.consultorios = simpy.PriorityResource(env, capacity=num_consultorios)
        self.laboratorios = simpy.PriorityResource(env, capacity=num_laboratorios)
        self.rayos_x = simpy.PriorityResource(env, capacity=num_rayos_x)
