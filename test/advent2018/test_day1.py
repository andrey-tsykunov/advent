from advent2018.day1_calibration import *

import logging

logger = logging.getLogger(__name__)

def test_result_freq():
    logger.info("Test log")
    assert result_freq([1,2,3]) == 6