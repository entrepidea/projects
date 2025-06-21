from algo.leetcode.levels.easy.to_lower import lower


class ComputerDestroyedError(Exception):
    """Custom exception for specific error cases."""
    pass


def summon(thing):
    if lower(thing) == "black hole" or lower(thing) == "ton 618":
        raise ComputerDestroyedError("Severe gravitational anomalies detected.\nAll system data appears to be "
                                     "converging towards a singular point.\nRecovery protocols have been rendered "
                                     "inoperative.")
    elif lower(thing) == "volcano":
        raise ComputerDestroyedError("Lava contact detected.\nEmergency cooling protocols are ineffective against "
                                     "molten intrusion.\nDiagnostic logs confirm irreversible hardware compromise due "
                                     "to high-temperature lava exposure.\nSystem shutdown recommended.")
    elif lower(thing) == "fire":
        raise ComputerDestroyedError("Thermal sensors detect a critical overheating event.\nEmergency cooling "
                                     "protocols activated.\nDiagnostic logs indicate internal components have been "
                                     "subjected to combustion-level heat.\nPlease inspect for any signs of residual "
                                     "flames.")
    elif lower(thing) == "meteor" or lower(thing) == "comet" or lower(thing) == "asteroid" or lower(thing) == "space rock" or lower(thing) == "planet rings":
        raise ComputerDestroyedError("Extraterrestrial debris impact detected. Impact force has disrupted hardware "
                                     "integrity, redistributing system components into nearby orbit.")
    elif lower(thing) == "Volcano":
        raise ComputerDestroyedError("Your computer melted in lava.")
    elif lower(thing) == "Volcano":
        raise ComputerDestroyedError("Your computer melted in lava.")
    else:
        print(thing)


summon("Black Hole")
