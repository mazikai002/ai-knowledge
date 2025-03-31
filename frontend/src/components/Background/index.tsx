import React, { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import styles from './Background.module.css';

const backgrounds = [
  {
    url: 'https://images.pexels.com/photos/1287075/pexels-photo-1287075.jpeg',
    description: '瑞士阿尔卑斯山脉'
  },
  {
    url: 'https://images.pexels.com/photos/1287076/pexels-photo-1287076.jpeg',
    description: '加拿大班夫国家公园'
  },
  {
    url: 'https://images.pexels.com/photos/1287077/pexels-photo-1287077.jpeg',
    description: '美国优胜美地国家公园'
  },
  {
    url: 'https://images.pexels.com/photos/1287078/pexels-photo-1287078.jpeg',
    description: '冰岛极光'
  },
  {
    url: 'https://images.pexels.com/photos/1287079/pexels-photo-1287079.jpeg',
    description: '新西兰米尔福德峡湾'
  },
  {
    url: 'https://images.pexels.com/photos/1287080/pexels-photo-1287080.jpeg',
    description: '马尔代夫环礁'
  },
  {
    url: 'https://images.pexels.com/photos/1287081/pexels-photo-1287081.jpeg',
    description: '美国大峡谷'
  },
  {
    url: 'https://images.pexels.com/photos/1287082/pexels-photo-1287082.jpeg',
    description: '挪威峡湾'
  }
];

const Background: React.FC = () => {
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % backgrounds.length);
    }, 20000);

    return () => clearInterval(timer);
  }, []);

  return (
    <div className={styles.backgroundContainer}>
      <AnimatePresence mode="wait">
        <motion.div
          key={currentIndex}
          className={styles.backgroundImage}
          initial={{ opacity: 0, scale: 1.1 }}
          animate={{ opacity: 1, scale: 1 }}
          exit={{ opacity: 0, scale: 0.9 }}
          transition={{ 
            duration: 2,
            ease: "easeInOut"
          }}
          style={{
            backgroundImage: `url(${backgrounds[currentIndex].url})`
          }}
        >
          <div className={styles.overlay} />
          <div className={styles.imageDescription}>
            {backgrounds[currentIndex].description}
          </div>
        </motion.div>
      </AnimatePresence>
    </div>
  );
};

export default Background; 